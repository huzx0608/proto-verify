package com.zetyun.rt.watcher;

import com.zetyun.rt.models.ChangeRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ChangeLogReader implements FileChangeWatcher {
  int MAX_ENTRIES_TO_READ = 100;
  private final String changeLogDir;
  Lock lock;
  private Condition condition;

  public ChangeLogReader(String changeLogDir) {
    this.changeLogDir = changeLogDir;
    lock = new ReentrantLock();
    condition = lock.newCondition();

  }

  /**
   * Blocking call
   * @param record
   * @return
   */
  public List<ChangeRecord> getChangeSince(ChangeRecord record) {
    List<ChangeRecord> changes = new ArrayList<ChangeRecord>();
    String fileName;
    long endOffset;
    if (record == null) {
      fileName = "log.1";
      endOffset = 0;
    } else {
      fileName = record.getChangeLogFileName();
      endOffset = record.getEndOffset();
    }
    try {
      lock.lock();

      File file;
      file = new File(changeLogDir + "/" + fileName);
      while (!file.exists() || file.length() <= endOffset) {
        // wait
        try {
          System.out.println("Waiting for new changes");
          condition.await();
          System.out.println("Detected changes");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      RandomAccessFile raf = new RandomAccessFile(changeLogDir + "/" + fileName, "r");
      raf.seek(endOffset);
      // out.writeLong(record.txid);
      // out.writeShort(record.type);
      // out.writeLong(record.timestamp);
      // out.writeUTF(record.file);

      int count = 0;
      do {
        ChangeRecord newRecord = new ChangeRecord();
        newRecord.setChangeLogFileName(fileName);
        newRecord.setStartOffset(raf.getFilePointer());
        newRecord.setTxid(raf.readLong());
        newRecord.setType(raf.readShort());
        newRecord.setTimestamp(raf.readLong());
        newRecord.setFile(raf.readUTF());
        newRecord.setEndOffset(raf.getFilePointer());
        changes.add(newRecord);
        count++;
      } while (count < MAX_ENTRIES_TO_READ && raf.getFilePointer() < raf.length());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
    return changes;
  }

  @Override
  public void onEntryModified(String path) {
    try {
      lock.lock();
      condition.signalAll();
    } catch (Exception e) {
      // TODO: handle exception
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void onEntryAdded(String path) {
    try {
      lock.lock();
      condition.signalAll();
    } catch (Exception e) {
      // TODO: handle exception
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void onEntryDeleted(String path) {
    try {
      lock.lock();
      condition.signalAll();
    } catch (Exception e) {
      // TODO: handle exception
    } finally {
      lock.unlock();
    }
  }
}
