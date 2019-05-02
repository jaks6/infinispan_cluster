import org.infinispan.manager.DefaultCacheManager;

public class Main {

    private static boolean isDataSource;

    public static void main(String[] args) {
        InfinispanReplicatedNode node = new InfinispanReplicatedNode();
        DefaultCacheManager cacheManager = node.getCacheManager();

        if (args.length > 0 && args[0].equals("dataSource")){
            isDataSource = true;
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> node.exit() ));


        System.out.printf("Starting logic with cluster %s; ROLE_DATA_SOURCE=%s\n", cacheManager.getClusterName(), isDataSource);

        new Thread(() -> {
            while ( true ) {
                if (isDataSource) {
                    updateCache(cacheManager);
                } else {
                    readCache(cacheManager);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }
        }).start();

    }

    private static void readCache(DefaultCacheManager cacheManager) {
        Object millis = cacheManager.getCache().get("millis");
        if (millis != null)
            System.out.printf("V: %s\n", (long) millis);
        else
            System.out.println("V: null");
    }

    private static void updateCache(DefaultCacheManager cacheManager) {
        long millis = System.currentTimeMillis();
        cacheManager.getCache().put("millis", millis);

    }
}
