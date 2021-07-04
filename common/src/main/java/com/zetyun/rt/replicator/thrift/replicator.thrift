namespace java com.zetyun.rt.replicator

struct ReplicateReq {
   # The largest sequence number currently in the local DB. Request for updates
   # of sequence (seq_no + 1) and larger
   1: required i64 seqNo,

   # The name of the db replicating from
   2: required string dbName,

   # If the requested data is available, the server will reply immediately.
   # Otherwise, it will wait for this amount of time before replying with an
   # empty response.
   # A value of 0 means no wait at all.
   3: required i64 maxWaitMs,

   # A server may return more than one update for a request. max_updates is the
   # uppper limit set by client side.
   # A value of 0 means no limit
   4: required i32 maxUpdates
}

struct Update {
   1: required binary    rawData,
   2: required i64       timestamp
}

struct ReplicateRsp {
   1: required list<Update> updates,
}

enum ErrorCode {
   OTHER = 0,
   SOURCE_NOT_FOUND = 1, # could not find the upstream db
   SOURCE_READ_ERROR = 2,

   # upstream db was recently removed. This is an optimization to reduce reads from
   # helix, and there is no harm if the upstream fails to distinguish between
   # SOURCE_REMOVED and SOURCE_NOT_FOUND
   SOURCE_REMOVED = 3,
}

exception ReplicateException {
   1: required string msg,
   2: required ErrorCode code,
}

service Replicator {
   ReplicateRsp replicate(1:ReplicateReq request)
      throws (1:ReplicateException e)
}

