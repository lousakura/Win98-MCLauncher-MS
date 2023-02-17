package com.google.common.eventbus;

import com.google.common.collect.Multimap;

interface SubscriberFindingStrategy {
  Multimap<Class<?>, EventSubscriber> findAllSubscribers(Object paramObject);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\google\common\eventbus\SubscriberFindingStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */