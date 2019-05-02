In-Memory Data Grid (IMDG) basic example with Infinispan using Peer-to-peer data replication

## Usage

Running this application starts an infinispan node. The node performs an operation every 500ms, depending on the argument:

1) with argument "dataSource", it stores the nodes current System.timeInMillis in the cache under the key "millis"
1) without arguments/other arguments, it prints the cache value under key "millis"

### Example:
(Build jar first)
java -jar imdg-test-1.0-SNAPSHOT.jar "dataSource"
