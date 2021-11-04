/**
 * Autogenerated by Thrift Compiler (0.14.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.zetyun.rt.replicator;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.14.1)", date = "2021-09-17")
public class ReplicateReq implements org.apache.thrift.TBase<ReplicateReq, ReplicateReq._Fields>, java.io.Serializable, Cloneable, Comparable<ReplicateReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReplicateReq");

  private static final org.apache.thrift.protocol.TField SEQ_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("seqNo", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField DB_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dbName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField MAX_WAIT_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("maxWaitMs", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField MAX_UPDATES_FIELD_DESC = new org.apache.thrift.protocol.TField("maxUpdates", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new ReplicateReqStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new ReplicateReqTupleSchemeFactory();

  public long seqNo; // required
  public @org.apache.thrift.annotation.Nullable java.lang.String dbName; // required
  public long maxWaitMs; // required
  public int maxUpdates; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SEQ_NO((short)1, "seqNo"),
    DB_NAME((short)2, "dbName"),
    MAX_WAIT_MS((short)3, "maxWaitMs"),
    MAX_UPDATES((short)4, "maxUpdates");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // SEQ_NO
          return SEQ_NO;
        case 2: // DB_NAME
          return DB_NAME;
        case 3: // MAX_WAIT_MS
          return MAX_WAIT_MS;
        case 4: // MAX_UPDATES
          return MAX_UPDATES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    @org.apache.thrift.annotation.Nullable
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __SEQNO_ISSET_ID = 0;
  private static final int __MAXWAITMS_ISSET_ID = 1;
  private static final int __MAXUPDATES_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SEQ_NO, new org.apache.thrift.meta_data.FieldMetaData("seqNo", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DB_NAME, new org.apache.thrift.meta_data.FieldMetaData("dbName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.MAX_WAIT_MS, new org.apache.thrift.meta_data.FieldMetaData("maxWaitMs", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MAX_UPDATES, new org.apache.thrift.meta_data.FieldMetaData("maxUpdates", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReplicateReq.class, metaDataMap);
  }

  public ReplicateReq() {
  }

  public ReplicateReq(
    long seqNo,
    java.lang.String dbName,
    long maxWaitMs,
    int maxUpdates)
  {
    this();
    this.seqNo = seqNo;
    setSeqNoIsSet(true);
    this.dbName = dbName;
    this.maxWaitMs = maxWaitMs;
    setMaxWaitMsIsSet(true);
    this.maxUpdates = maxUpdates;
    setMaxUpdatesIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReplicateReq(ReplicateReq other) {
    __isset_bitfield = other.__isset_bitfield;
    this.seqNo = other.seqNo;
    if (other.isSetDbName()) {
      this.dbName = other.dbName;
    }
    this.maxWaitMs = other.maxWaitMs;
    this.maxUpdates = other.maxUpdates;
  }

  public ReplicateReq deepCopy() {
    return new ReplicateReq(this);
  }

  @Override
  public void clear() {
    setSeqNoIsSet(false);
    this.seqNo = 0;
    this.dbName = null;
    setMaxWaitMsIsSet(false);
    this.maxWaitMs = 0;
    setMaxUpdatesIsSet(false);
    this.maxUpdates = 0;
  }

  public long getSeqNo() {
    return this.seqNo;
  }

  public ReplicateReq setSeqNo(long seqNo) {
    this.seqNo = seqNo;
    setSeqNoIsSet(true);
    return this;
  }

  public void unsetSeqNo() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SEQNO_ISSET_ID);
  }

  /** Returns true if field seqNo is set (has been assigned a value) and false otherwise */
  public boolean isSetSeqNo() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SEQNO_ISSET_ID);
  }

  public void setSeqNoIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SEQNO_ISSET_ID, value);
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.String getDbName() {
    return this.dbName;
  }

  public ReplicateReq setDbName(@org.apache.thrift.annotation.Nullable java.lang.String dbName) {
    this.dbName = dbName;
    return this;
  }

  public void unsetDbName() {
    this.dbName = null;
  }

  /** Returns true if field dbName is set (has been assigned a value) and false otherwise */
  public boolean isSetDbName() {
    return this.dbName != null;
  }

  public void setDbNameIsSet(boolean value) {
    if (!value) {
      this.dbName = null;
    }
  }

  public long getMaxWaitMs() {
    return this.maxWaitMs;
  }

  public ReplicateReq setMaxWaitMs(long maxWaitMs) {
    this.maxWaitMs = maxWaitMs;
    setMaxWaitMsIsSet(true);
    return this;
  }

  public void unsetMaxWaitMs() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MAXWAITMS_ISSET_ID);
  }

  /** Returns true if field maxWaitMs is set (has been assigned a value) and false otherwise */
  public boolean isSetMaxWaitMs() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MAXWAITMS_ISSET_ID);
  }

  public void setMaxWaitMsIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MAXWAITMS_ISSET_ID, value);
  }

  public int getMaxUpdates() {
    return this.maxUpdates;
  }

  public ReplicateReq setMaxUpdates(int maxUpdates) {
    this.maxUpdates = maxUpdates;
    setMaxUpdatesIsSet(true);
    return this;
  }

  public void unsetMaxUpdates() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __MAXUPDATES_ISSET_ID);
  }

  /** Returns true if field maxUpdates is set (has been assigned a value) and false otherwise */
  public boolean isSetMaxUpdates() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __MAXUPDATES_ISSET_ID);
  }

  public void setMaxUpdatesIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __MAXUPDATES_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, @org.apache.thrift.annotation.Nullable java.lang.Object value) {
    switch (field) {
    case SEQ_NO:
      if (value == null) {
        unsetSeqNo();
      } else {
        setSeqNo((java.lang.Long)value);
      }
      break;

    case DB_NAME:
      if (value == null) {
        unsetDbName();
      } else {
        setDbName((java.lang.String)value);
      }
      break;

    case MAX_WAIT_MS:
      if (value == null) {
        unsetMaxWaitMs();
      } else {
        setMaxWaitMs((java.lang.Long)value);
      }
      break;

    case MAX_UPDATES:
      if (value == null) {
        unsetMaxUpdates();
      } else {
        setMaxUpdates((java.lang.Integer)value);
      }
      break;

    }
  }

  @org.apache.thrift.annotation.Nullable
  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case SEQ_NO:
      return getSeqNo();

    case DB_NAME:
      return getDbName();

    case MAX_WAIT_MS:
      return getMaxWaitMs();

    case MAX_UPDATES:
      return getMaxUpdates();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case SEQ_NO:
      return isSetSeqNo();
    case DB_NAME:
      return isSetDbName();
    case MAX_WAIT_MS:
      return isSetMaxWaitMs();
    case MAX_UPDATES:
      return isSetMaxUpdates();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that instanceof ReplicateReq)
      return this.equals((ReplicateReq)that);
    return false;
  }

  public boolean equals(ReplicateReq that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_seqNo = true;
    boolean that_present_seqNo = true;
    if (this_present_seqNo || that_present_seqNo) {
      if (!(this_present_seqNo && that_present_seqNo))
        return false;
      if (this.seqNo != that.seqNo)
        return false;
    }

    boolean this_present_dbName = true && this.isSetDbName();
    boolean that_present_dbName = true && that.isSetDbName();
    if (this_present_dbName || that_present_dbName) {
      if (!(this_present_dbName && that_present_dbName))
        return false;
      if (!this.dbName.equals(that.dbName))
        return false;
    }

    boolean this_present_maxWaitMs = true;
    boolean that_present_maxWaitMs = true;
    if (this_present_maxWaitMs || that_present_maxWaitMs) {
      if (!(this_present_maxWaitMs && that_present_maxWaitMs))
        return false;
      if (this.maxWaitMs != that.maxWaitMs)
        return false;
    }

    boolean this_present_maxUpdates = true;
    boolean that_present_maxUpdates = true;
    if (this_present_maxUpdates || that_present_maxUpdates) {
      if (!(this_present_maxUpdates && that_present_maxUpdates))
        return false;
      if (this.maxUpdates != that.maxUpdates)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(seqNo);

    hashCode = hashCode * 8191 + ((isSetDbName()) ? 131071 : 524287);
    if (isSetDbName())
      hashCode = hashCode * 8191 + dbName.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(maxWaitMs);

    hashCode = hashCode * 8191 + maxUpdates;

    return hashCode;
  }

  @Override
  public int compareTo(ReplicateReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.compare(isSetSeqNo(), other.isSetSeqNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSeqNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.seqNo, other.seqNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetDbName(), other.isSetDbName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDbName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dbName, other.dbName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetMaxWaitMs(), other.isSetMaxWaitMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaxWaitMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.maxWaitMs, other.maxWaitMs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.compare(isSetMaxUpdates(), other.isSetMaxUpdates());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaxUpdates()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.maxUpdates, other.maxUpdates);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  @org.apache.thrift.annotation.Nullable
  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("ReplicateReq(");
    boolean first = true;

    sb.append("seqNo:");
    sb.append(this.seqNo);
    first = false;
    if (!first) sb.append(", ");
    sb.append("dbName:");
    if (this.dbName == null) {
      sb.append("null");
    } else {
      sb.append(this.dbName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("maxWaitMs:");
    sb.append(this.maxWaitMs);
    first = false;
    if (!first) sb.append(", ");
    sb.append("maxUpdates:");
    sb.append(this.maxUpdates);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'seqNo' because it's a primitive and you chose the non-beans generator.
    if (dbName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'dbName' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'maxWaitMs' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'maxUpdates' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ReplicateReqStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ReplicateReqStandardScheme getScheme() {
      return new ReplicateReqStandardScheme();
    }
  }

  private static class ReplicateReqStandardScheme extends org.apache.thrift.scheme.StandardScheme<ReplicateReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReplicateReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SEQ_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.seqNo = iprot.readI64();
              struct.setSeqNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DB_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dbName = iprot.readString();
              struct.setDbNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MAX_WAIT_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.maxWaitMs = iprot.readI64();
              struct.setMaxWaitMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MAX_UPDATES
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.maxUpdates = iprot.readI32();
              struct.setMaxUpdatesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetSeqNo()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'seqNo' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetMaxWaitMs()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'maxWaitMs' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetMaxUpdates()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'maxUpdates' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReplicateReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SEQ_NO_FIELD_DESC);
      oprot.writeI64(struct.seqNo);
      oprot.writeFieldEnd();
      if (struct.dbName != null) {
        oprot.writeFieldBegin(DB_NAME_FIELD_DESC);
        oprot.writeString(struct.dbName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(MAX_WAIT_MS_FIELD_DESC);
      oprot.writeI64(struct.maxWaitMs);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(MAX_UPDATES_FIELD_DESC);
      oprot.writeI32(struct.maxUpdates);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReplicateReqTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public ReplicateReqTupleScheme getScheme() {
      return new ReplicateReqTupleScheme();
    }
  }

  private static class ReplicateReqTupleScheme extends org.apache.thrift.scheme.TupleScheme<ReplicateReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReplicateReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI64(struct.seqNo);
      oprot.writeString(struct.dbName);
      oprot.writeI64(struct.maxWaitMs);
      oprot.writeI32(struct.maxUpdates);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReplicateReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.seqNo = iprot.readI64();
      struct.setSeqNoIsSet(true);
      struct.dbName = iprot.readString();
      struct.setDbNameIsSet(true);
      struct.maxWaitMs = iprot.readI64();
      struct.setMaxWaitMsIsSet(true);
      struct.maxUpdates = iprot.readI32();
      struct.setMaxUpdatesIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
