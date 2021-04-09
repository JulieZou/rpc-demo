package com.ibicd.rpc.registry;

import java.net.URI;
import java.util.Set;

/**
 * @ClassName NotifyListener
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/8 21:32
 * @Version 1.0
 */
public interface NotifyListener {

    void notify(Set<URI> uris);
}
