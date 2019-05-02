
import java.util.UUID;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.context.Flag;
import org.infinispan.manager.DefaultCacheManager;

public class InfinispanReplicatedNode {
    private final DefaultCacheManager cacheManager;

    public DefaultCacheManager getCacheManager() {
        return cacheManager;
    }

    public InfinispanReplicatedNode() {
        // Setup up a clustered cache manager
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        global.defaultCacheName("myCache");
        // Make the default cache a replicated synchronous one
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clustering().cacheMode(CacheMode.REPL_SYNC);
        // Initialize the cache manager
        cacheManager = new DefaultCacheManager(global.build(), builder.build());

        System.out.printf("Started node - cluster: %s, node address: %s\n", cacheManager.getClusterName(), cacheManager.getNodeAddress());
    }

    public void exit(){
        System.out.println("\n\n Killing infinispan node... \n\n");
        cacheManager.stop();
    }
}

