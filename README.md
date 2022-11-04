# Disruptor 

<img src="./images/disruptor.png" />


## Disruptor là gì?

[LMAX](https://www.lmax.com/) đặt mục tiêu trở thành nền tảng giao dịch nhanh nhất thế giới, vì vậy họ đã thử nghiệm để giảm latency thấp nhất. Performance testing cho thấy việc sử dụng queue để pass dữ liệu giữa các stage của hệ thống có độ trễ, vì vậy họ tập trung vào việc tối ưu hoá phần này. Và sau thời gian nghiên cứu và thử nghiệm, họ cho ra sản phẩm là `Disruptor`.

LMAX-Disruptor là một bộ thư viện giúp cho việc phát triển các ứng dụng với độ tải lớn (high-performance) cho phép xử lý đồng thời (concurrency) một số lượng rất lớn message mà không cần Lock (lock-free). Nếu bạn làm việc với Java thì thực tế đây là bộ thư viện về Concurrency tốt nhất và nhanh nhất hiện nay.

## Tại sao không phải là queue?

Việc sử dụng queue có thể sảy ra tranh chấp tại 2 điểm đầu và cuối(head, tail). Queue thường luôn gần đầy hoặc trống. Việc đó cho thấy sự khác biệt về tốc độ giữa consumer và producer. Chúng ít khi hoạt động gần như là cân bằng về hiệu suất.

Để tránh `write contention`, queue thường sử dụng locks, điều này làm giảm hiệu suất và trong ngữ cảnh kernel có thể làm mất cache.

Nếu hai thread riêng biệt ghi vào hai giá trị khác nhau, mỗi thread có thể sẽ sẽ ảnh hưởng tới dữ liệu bên thread khác thực hiên. Đó là sự tranh chấp giữa hai thread mặc dù chúng đang ghi trên hai biến khác nhau. Điều này được gọi là [false sharing](https://dzone.com/articles/false-sharing), bởi vì mỗi lần truy cập và để lấy head thì cũng phải lấy tail và ngược lại.

Một vấn đề nữa với Traditional Queues là nó sẽ lưu trữ dự liệu theo kiểu vào trước ra trước (FIFO). Vấn đề là khi queue trong trạng thái gần rỗng thì rất có thể dữ liệu ở đầu, cuối và queue-size có thể ở cùng cache line dẫn tới vấn đề về false sharing 


## Disruptor hoạt động như thế nào?

<img src="./images/RingBuffer.jpg"/>

Ring buffer trong disruptor được filled các object trước khi thực hiện. Produces và consumers thực hiện writing hoặc reading mà không bị locking hay tranh chấp.

Trong Disruptor, tất cả event được published cho tất cả consumers(multi cast), để parallel consumption thông qua các downstream queues riêng biệt.

Producers và consumers có một bộ đếm trình tự để chỉ ra vị trí nào trong buffer mà nó hiện đang làm việc. Mỗi producer/consumer có thể viết bộ đếm trình tự riêng của mình nhưng có thể đọc bộ đếm trình tự của người khác. Producers và consumers đọc các vị trí đảm bảo vị trí mà nó muốn ghi có sẵn mà không có bất kỳ khóa nào.


## Tại sao Disruptor lại tốt?

Disruptor không sử dụng locks mà sử dụng CAS(Compare And Swap/Set). Nó hoạt động giống như optimistic lock.

Chính nhờ sử dụng ring-buffer mà Producer có thể ghi dữ liệu trước vào vùng nhớ đệm (pre-allocate memory for events), nhờ việc đó mà giúp tránh được vấn đề về lock và contention bởi vì ở cấp độ hard-ware thì entries có thể được nạp trước (pre-load) và ring buffer, nhờ thế CPU process không cần phải quay lại main memory (L3 hoặc Ram) để lấy tiếp các item trong ring buffer. Và các event có thể được sử dụng lại (object reuse) trong suốt thời gian Disruptor đang được hoạt động và tránh các rắc rối với garbage collector.

Magic cache line padding: Giải pháp tránh `false sharing`, chắc chắn rằng sequence của ring buffer sẽ không nằm chung cache line với bất kỳ giá trị nào khác. Khi get sequence sẽ chỉ cần lấy giá trị ở L1 thay vì phải lên L3 hoặc memory.

<img src="./images/memory-layers.png"/>

***Mỗi CPU core đều có bộ nhớ cache riêng đó là bộ nhớ L1, L2 và bộ nhớ chung (share memory) L3 cho tất cả các core (CPU core) và CPU sẽ cache data lên đó. Vì vậy thay vì chương tình sẽ truy vấn và DRAM thì chương trình sẽ truy vấn vào bộ nhớ riêng L1, L2 trước nếu không thấy thông tin hợp lệ thì mới truy cập vào share memory L3 hoặc main memory (DRAM). Chú ý rằng bộ nhớ của L1 và L2 khá nhỏ 32kb với L1 và 256kb với L2.***

## Tại sao ring size phải là luỹ thừa của 2?

Nếu size của ring là 2^n, ring buffer sẽ sử dụng masking thay vì toán tử modulo để tìm vị trị trong ring.

Ví dụ:
```log
size = 32
bin(size) => '00100000'

mask = size - 1;
bin(mask) => '00011111'

sequence = 4
bin(4 & mask) => '00000100' (4)

sequence = 32
bin(sequence & mask) => '00000000' (0)

sequence = 33
bin(sequence & mask) => '00000001' (1)
```

Như ví dụ trên, nếu sequence=33, thì vì muốn tìm index trong ring ta sẽ dùng `index=33%32=1`. Thay vào đó ta chỉ cần `bin(33 & mask) => 00000001 ~ 1`. Kết quả tìm kiếm sẽ nhanh hơn nhiều so với toán tử modulo.

## Sử dụng thư viện Disruptor với java

### Thêm dependency

```java
<dependency>
    <groupId>com.lmax</groupId>
    <artifactId>disruptor</artifactId>
    <version>3.3.6</version>
</dependency>
```

### Defining an event

Tạo một event để chứa các dữ liệu.

```java
@Setter
@Getter
public class StorageEvent {
  public static final EventFactory<StorageEvent> EVENT_FACTORY =
      new EventFactory<StorageEvent>() {
        @Override
        public StorageEvent newInstance() {
          return new StorageEvent();
        }
      };
  Future<List<String>> future;
  Tracker tracker;
  SQLClientProvider provider;
}
```
EventFactory giúp Disruptor có thể chủ động pre-allocate các event vào Ring-Buffer.
### Consumer

Tạo một Consumer để lấy các dữ liệu từ Ring-Buffer về và implement lại nó bằng các Override hàm onEvent().

```java

public class StorageConsumer implements WorkHandler<StorageEvent> {
  String SELECT_EXAMPLE = "SELECT * FROM person.account where  id=1001";

  @Override
  public void onEvent(StorageEvent storageEvent) throws Exception {
    Connection connection = storageEvent.getProvider().getConnection();
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery(SELECT_EXAMPLE);
    storageEvent.getTracker().record();
    List<String> uids = new ArrayList<>();
    while (rs.next()) {
      uids.add(rs.getString("uid"));
    }
    storageEvent.getFuture().complete(uids);
    rs.close();
    stmt.close();
    connection.close();
  }
}
```

### Khởi tạo Disruptor

```java

static WorkHandler<StorageEvent>[] getWorkersPool(int numWorkers) {
    WorkHandler<StorageEvent>[] workHandlers = new WorkHandler[numWorkers];
    for (int i = 0; i < numWorkers; i++) {
      workHandlers[i] = new StorageConsumer();
    }
    return workHandlers;
  }



public static <T> Disruptor newInstance() {
    ThreadFactory threadFactory = DaemonThreadFactory.INSTANCE;
    int bufferSize = 1024;
    WaitStrategy waitStrategy = new BusySpinWaitStrategy();

    Disruptor<ValueEvent> disruptor = new Disruptor<>(
    ValueEvent.EVENT_FACTORY, 
    bufferSize, 
    threadFactory, 
    ProducerType.MULTI, 
    waitStrategy);

    Disruptor disruptor =
        new Disruptor(
            factory,
            disruptorConfig.getBufferSize(),
            DaemonThreadFactory.INSTANCE,
            producerType,
            new BusySpinWaitStrategy());

    disruptor.handleEventsWithWorkerPool(getWorkersPool(64));

    return disruptor;
  }

```
Các tham số trong khởi tạo disruptor:

- Event Factory – Tạo đối tượng và lưu trữ trong ring buffer khi khơi tạo disruptor.
- The size of Ring Buffer – Kích thước của ringbuffer. Disruptor bắt buộc giá trị này phải là luỹ thừa của 2.
- Thread Factory –  Sử dụng ThreadFactory để khởi tạo các thread cho các bộ sử lý (processors) cho các event.
- Producer Type — Chỉ định việc Disruptor làm việc trên một (single) hoặc nhiều (multiple) Producer bằng cách truyền tham số Producer.SINGLE hoặc Producer.MULTI
- Waiting strategy – Để định nghĩa các chiến thuật (strategy) chờ đợi để sử lý các vấn đề chênh lệch tốc độ gửi và nhận của Producer và Consumer hay còn gọi là "Producer and Consumer problem".


```
// Start disruptor
RingBuffer<ValueEvent> ringBuffer = disruptor.start();
```

### Producing và Publishing event

```java
long sequenceId = ringBuffer.next();
StorageEvent storageEvent = ringBuffer.get(sequenceId);
storageEvent.setProvider(clientProvider);
storageEvent.setFuture(future);
storageEvent.setTracker(tracker);
ringBuffer.publish(sequenceId);
```

## Apply với vertx

Mô hình apply disruptor với vertx

<img src="./images/apply-vertx.png"/>

Hệ thống sẽ dùng disruptor trong việc thao tác với cơ sở dữ liệu, thay thế cho worker pool trong vertx-jdbc


## Benchmark

### Setup

Môi trường:
- Server: 10.50.1.22
- Client: 10.50.1.23
- Prometheus: 10.50.1.22
- Tidb server: 10.30.17.173

Kịch bản: Dùng [ghz](https://ghz.sh/) để bắn request trong vòng 10 phút và đo kết quả `latency(p99)`, `throughput` cho đến khi kết quả đạt mức ổn định.

### Cấu hình tham số của server

|Worker pool size vertx|Number instant grpc vertx|Connection pool|
|--|--|--|
|64|32|64|

### Tuning tham số

#### Wait Strategies 

<img src="./images/Wait-Strategies.png"/>

- ***BlockingWaitStrategy***:
Mặc định Disruptor sẽ sử dụng Wait Strategy là BlockingWaitStrategy, trong nội bộ (code) của BlockingWaitStrategy nó sử dụng ReentrantLock để đồng bộ hóa (synchronized) và dùng Condition để wait và notify các Producer và Consumer để giải quyết các vấn đề về “Producer–consumer problem”.
Đây là cách sử lý chậm nhất trong các wait strategies (vì sử dụng cơ chế Looking), nhưng đây là cách sử dụng an toàn và thân thiện nhất với tài nguyên của CPU, có nghĩa là Disruptor sẽ không gây áp lực lên CPU nhưng đánh đổi lại là tốc độ sử lý của Disruptor. Và đây là cách người viết khuyên dùng khi hệ thống của bạn lượng tài nguyên CPU hạn chế hoặc CPU hay bị tình trạng quá tải (high load).

- ***SleepingWaitStrategy***:
Giống như BlockingWaitStrategy đây cũng là một cách sử dụng khá thân thiện với CPU, bằng cách sử dụng kỹ thuật "busy wait loop" bằng cách sử dụng LockSupport.parkNanos(1) để dừng (pause) thread hiện tại của Producer hoặc Consumer để wait hoặc notify khi có điều kiện thỏa mãn (Ring buffer đầy hoặc rỗng hoặc hết đầy hoặc có dữ liệu tùy từng trường hợp), giá trị mặc định mỗi lần pause thread (DEFAULT_SLEEP) là 100 nanoseconds và sẽ lặp lại 200 lần cho tới khi có điều kiện thoả mãn trên xuất hiện.
Tuy không sử dụng cơ chế Looking (look-free) nhưng các thread liên tục phải waiting do đó độ trễ sẽ cao (high latency) cho nên tốc độ thực thi của Disruptor sẽ không cao, nhưng bù lại các thread/process sẽ không bị các trường hợp về context switching, do đó số lượng thread/process tạo ra sẽ không nhiều và CPU sẽ không bị stress.
Với trường hợp ứng dụng cần độ trễ thấp (low latency) thì đây là cách không nên sử dụng, nhưng lại rất phù hợp với các chức năng không quan tâm tới độ trễ, ví dụ như tính năng ghi log hệ thống chẳng hạn (asynchronous logging).

- ***YieldingWaitStrategy***:
YieldingWaitStrategy cũng là một kiểu Wait Strategy giống như SleepingWaitStrategy nhưng có độ trễ thấp hơn (low latency) bằng cách sử dụng chiến thuật "busy spin waiting". Bên trong YieldingWaitStrategy sẽ sử dụng method Thread.yield() để khi Ring Buffer đang rảnh rỗi thì sẽ nhường và ưu tiên các Thread khác được phép chạy trước.
Đây là chiến thuật được khuyến khích khi ứng dụng của bạn cần hiệu suất cao (high performance) và số lượng luồng (thread) của Consumer (Event Handler) nhỏ hơn số lượng logical cores của CPU. Có nghĩa là CPU của bạn có hỗ trợ công nghệ và bật chế độ Hyper Threading, và số lõi logical nhiều hơn số lượng của Multi-cast Consumer.

- ***BusySpinWaitStrategy***:
Tốc độ thực thi của BusySpinWaitStrategy là nhanh nhất trong các Wait Strategy nhưng nó lại gây áp lực (stress) lên tài nguyên hệ thống ở đây là CPU nhiều nhất, nó chỉ nên được sử dụng trong trường hợp số lượng luồng của Consumer (Event Handler) nhỏ hơn số lượng lõi vật lý (physical cores) của CPU.

#### Producer type

MULTI, SINGLE

#### Number worker

256, 128, 64, 32

#### Ring buffer size

65536, 64

### Kết quả:


- Sử dụng JDBC-vertx

||Throughput|Latency(p99)|
|--|--|--|
|JDBC-Vertx|11392|0.020431|

- Sử dụng Disruptor
  
|Disruptor worker |Ring size|Producer type|WaitStrategy|Throughput|Latency(p99)|
|--|--|--|--|--|--|--|
|256|65536|MULTI|BusySpinWaitStrategy|12421|0.020187|
|128|65536|MULTI|BusySpinWaitStrategy|16055|0.018122|
|64|65536|MULTI|BusySpinWaitStrategy|32637|0.007099|
|64|1024|MULTI|BusySpinWaitStrategy|320001|0.007123|
|32|65536|MULTI|BusySpinWaitStrategy|25236|0.0090031|
|64|65536|SINGLE|BusySpinWaitStrategy |230|0.010551|
|64|65536|MULTI|BlockingWaitStrategy |33355|0.006878|


Kết quả cho thấy:
- Number Worker:  Khi số worker trong disruptor bằng số connection pool thì kết quả đạt ngưỡng cao nhất.
-  Producer type: MULTI producer performance sẽ cao hơn với SINGLE producer
-  WaitStrategy: Kết quả chênh lệch không đáng kể.
-  Buffer size ring: Kết quả chênh lệch không đáng kể.

## Tham khảo

- [Blogs and articles](https://github.com/LMAX-Exchange/disruptor/wiki/Blogs-And-Articles)
- [Concurrency with LMAX Disruptor](https://www.baeldung.com/lmax-disruptor-concurrency)
- [Understanding the LMAX Disruptor](https://itnext.io/understanding-the-lmax-disruptor-caaaa2721496)
- [High Performant Event Collector with Disruptor and Vert.x](https://medium.com/@mykidong/howto-high-performant-event-collector-with-disruptor-and-vert-x-2e1a1949a62c)
- [Getting the Disruptor](https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started#basic-tuning-options)







