package com.zetyun.dingo.avo;

import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;
import jdk.nashorn.internal.codegen.types.Type;
import org.apache.avro.*;
import org.apache.avro.generic.GenericData;
import org.apache.avro.io.*;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;
import org.apache.calcite.avatica.SqlType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
//import java.util.Date;

public class AvroExample {

    public static void main(String[] args) throws Exception {
//        Dummy expected = new Dummy();
//        expected.setDate(new Date());
//        System.out.println("EXPECTED: " + expected);
//        Schema schema = ReflectData.get().getSchema(Dummy.class);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Encoder encoder = EncoderFactory.get().binaryEncoder(baos, null);
//        DatumWriter<Dummy> writer = new ReflectDatumWriter<Dummy>(schema);
//        try {
//            writer.write(expected, encoder);
//            encoder.flush();
//            Decoder decoder = DecoderFactory.get().binaryDecoder(baos.toByteArray(), null);
//            DatumReader<Dummy> reader = new ReflectDatumReader<Dummy>(schema);
//            Dummy actual = reader.read(null, decoder);
//            System.out.println("ACTUAL: " + actual);
//        } catch (IOException e) {
//            System.err.println("IOException: " + e.getMessage());
//        }


        // Long time = new Date(2021, 1, 1).getTime();
//        java.sql.Date date = new Date(2020, 1, 1);
//        System.out.println(date.getTime());
//
//        // TimeZone timeZone = TimeZone.getTimeZone("GMT");
//        String s = "2020-01-01";
//        // dingo: 1577836800000
//        //        1577577660000
//        TimeZone timeZone =  TimeZone.getDefault(); //TimeZone.getTimeZone("UTC");
//        DateFormat dateFormat = new SimpleDateFormat("YYYY-mm-dd", Locale.ROOT);
//        Calendar ret = Calendar.getInstance(timeZone, Locale.ROOT);
//        dateFormat.setCalendar(ret);
//        dateFormat.setLenient(false);
//        ParsePosition pp = new ParsePosition(0);
//        final java.util.Date d = dateFormat.parse(s, pp);
//        System.out.println(d.getTime());
//        ret.setTime(d);
//        ret.setTimeZone(TimeZone.getTimeZone("UTC"));
//        System.out.println(ret.getTime());


//        ret.setTime(d);
//        GregorianCalendar calendarExpected = new GregorianCalendar(2020, 1, 1);
//        // calendarExpected.setTimeZone(timeZone);
//        System.out.println(calendarExpected.getTime().getTime());
        // 1577836800
        // 1580515200000
        // 1580486400000

        // AvroExample.convertDate();

        LocalDate localDate = LocalDate.of(2020, 1,1);
        System.out.println(localDate.toEpochDay());

        System.out.println(new Timestamp(1577836800000L).toLocalDateTime().toLocalDate().toEpochDay());
    }

    public static void convertDecimal() {
        Schema smallerSchema;
        LogicalType smallerLogicalType;
        smallerSchema = Schema.createFixed("smallFixed", null, null, 3);
        smallerSchema.addProp("logicalType", "decimal");
        smallerSchema.addProp("precision", 5);
        smallerSchema.addProp("scale", 1);
        smallerLogicalType = LogicalTypes.fromSchema(smallerSchema);

        Conversion<BigDecimal> CONVERSION = new Conversions.DecimalConversion();
        BigDecimal bigDecimal = new BigDecimal("1234.5");
        System.out.println(bigDecimal.precision());
        System.out.println(bigDecimal.scale());
        final byte[] bytes = bigDecimal.unscaledValue().toByteArray();

        LogicalTypes.Decimal decimal = (LogicalTypes.Decimal) smallerLogicalType;
        final BigDecimal fromFixed = CONVERSION.fromFixed(new GenericData.Fixed(smallerSchema, bytes), smallerSchema,
                decimal);
        System.out.println(fromFixed.toString());
    }

    public static void convertDate() throws IOException {
        EncoderFactory factory = new EncoderFactory();
        Schema schm = ReflectData.get().getSchema(AvroEncRecord.class);
        ReflectDatumWriter<AvroEncRecord> writer = new ReflectDatumWriter<>(schm);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        AvroEncRecord record = new AvroEncRecord();
        record.date = new java.util.Date(948833323L);
        writer.write(record, factory.directBinaryEncoder(out, null));

        ReflectDatumReader<AvroEncRecord> reader = new ReflectDatumReader<>(schm);
        AvroEncRecord decoded = reader.read(new AvroEncRecord(),
                DecoderFactory.get().binaryDecoder(out.toByteArray(), null));
        System.out.println(decoded.toString());
        System.out.println(record.toString());
    }

}
