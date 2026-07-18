package com.meharenterprises.originsms.ui.thread;

import com.meharenterprises.originsms.ui.thread.MessageAdapter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MessageAdapter.kt */
@Metadata(d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010'\n\u0000*\u0001\u0000\b\n\u0018\u00002\"\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001j\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0003`\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\bH\u0014¨\u0006\t"}, d2 = {"com/meharenterprises/originsms/ui/thread/MessageAdapter$previewCache$1", "Ljava/util/LinkedHashMap;", "", "Lcom/meharenterprises/originsms/ui/thread/MessageAdapter$LinkPreviewData;", "Lkotlin/collections/LinkedHashMap;", "removeEldestEntry", "", "e", "", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes10.dex */
public final class MessageAdapter$previewCache$1 extends LinkedHashMap<String, MessageAdapter.LinkPreviewData> {
    /* JADX INFO: Access modifiers changed from: package-private */
    public MessageAdapter$previewCache$1() {
        super(16, 0.75f, true);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object key) {
        if (key instanceof String) {
            return containsKey((String) key);
        }
        return false;
    }

    public /* bridge */ boolean containsKey(String key) {
        return super.containsKey((Object) key);
    }

    public /* bridge */ boolean containsValue(MessageAdapter.LinkPreviewData value) {
        return super.containsValue((Object) value);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object value) {
        if (value == null ? true : value instanceof MessageAdapter.LinkPreviewData) {
            return containsValue((MessageAdapter.LinkPreviewData) value);
        }
        return false;
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set<Map.Entry<String, MessageAdapter.LinkPreviewData>> entrySet() {
        return getEntries();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ MessageAdapter.LinkPreviewData get(Object key) {
        if (key instanceof String) {
            return get((String) key);
        }
        return null;
    }

    public /* bridge */ MessageAdapter.LinkPreviewData get(String key) {
        return (MessageAdapter.LinkPreviewData) super.get((Object) key);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object get(Object key) {
        if (key instanceof String) {
            return get((String) key);
        }
        return null;
    }

    public /* bridge */ Set<Map.Entry<String, MessageAdapter.LinkPreviewData>> getEntries() {
        return super.entrySet();
    }

    public /* bridge */ Set<String> getKeys() {
        return super.keySet();
    }

    public final /* bridge */ MessageAdapter.LinkPreviewData getOrDefault(Object key, MessageAdapter.LinkPreviewData defaultValue) {
        return !(key instanceof String) ? defaultValue : getOrDefault((String) key, defaultValue);
    }

    public /* bridge */ MessageAdapter.LinkPreviewData getOrDefault(String key, MessageAdapter.LinkPreviewData defaultValue) {
        return (MessageAdapter.LinkPreviewData) super.getOrDefault((Object) key, (String) defaultValue);
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object getOrDefault(Object key, Object defaultValue) {
        return !(key instanceof String) ? defaultValue : getOrDefault((String) key, (MessageAdapter.LinkPreviewData) defaultValue);
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    public /* bridge */ Collection<MessageAdapter.LinkPreviewData> getValues() {
        return super.values();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Set<String> keySet() {
        return getKeys();
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ MessageAdapter.LinkPreviewData remove(Object key) {
        if (key instanceof String) {
            return remove((String) key);
        }
        return null;
    }

    public /* bridge */ MessageAdapter.LinkPreviewData remove(String key) {
        return (MessageAdapter.LinkPreviewData) super.remove((Object) key);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ /* synthetic */ Object remove(Object key) {
        if (key instanceof String) {
            return remove((String) key);
        }
        return null;
    }

    @Override // java.util.HashMap, java.util.Map
    public final /* bridge */ boolean remove(Object key, Object value) {
        if (!(key instanceof String)) {
            return false;
        }
        if (value == null ? true : value instanceof MessageAdapter.LinkPreviewData) {
            return remove((String) key, (MessageAdapter.LinkPreviewData) value);
        }
        return false;
    }

    public /* bridge */ boolean remove(String key, MessageAdapter.LinkPreviewData value) {
        return super.remove((Object) key, (Object) value);
    }

    @Override // java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ int size() {
        return getSize();
    }

    @Override // java.util.LinkedHashMap, java.util.HashMap, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Collection<MessageAdapter.LinkPreviewData> values() {
        return getValues();
    }

    @Override // java.util.LinkedHashMap
    protected boolean removeEldestEntry(Map.Entry<String, MessageAdapter.LinkPreviewData> e) {
        Intrinsics.checkNotNullParameter(e, "e");
        return size() > 50;
    }
}
