namespace java com.zetyun.example.thrift

typedef i32 int

service AddService {
     int add(1:int param1, 2:int param2);
}
