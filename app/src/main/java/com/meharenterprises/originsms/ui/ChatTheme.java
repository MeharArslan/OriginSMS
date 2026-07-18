package com.meharenterprises.originsms.ui;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ChatThemeManager.kt */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0007\n\u0002\b*\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u0085\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\u0006\u0010\t\u001a\u00020\u0006\u0012\u0006\u0010\n\u001a\u00020\u0006\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\u0006\u0012\u0006\u0010\r\u001a\u00020\u0006\u0012\u0006\u0010\u000e\u001a\u00020\u0006\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\u0006\u0012\u0006\u0010\u0012\u001a\u00020\u0006\u0012\u0006\u0010\u0013\u001a\u00020\u0006\u0012\u0006\u0010\u0014\u001a\u00020\u0006¢\u0006\u0002\u0010\u0015J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0006HÆ\u0003J\t\u0010+\u001a\u00020\u0006HÆ\u0003J\t\u0010,\u001a\u00020\u0010HÆ\u0003J\t\u0010-\u001a\u00020\u0006HÆ\u0003J\t\u0010.\u001a\u00020\u0006HÆ\u0003J\t\u0010/\u001a\u00020\u0006HÆ\u0003J\t\u00100\u001a\u00020\u0006HÆ\u0003J\t\u00101\u001a\u00020\u0003HÆ\u0003J\t\u00102\u001a\u00020\u0006HÆ\u0003J\t\u00103\u001a\u00020\u0006HÆ\u0003J\t\u00104\u001a\u00020\u0006HÆ\u0003J\t\u00105\u001a\u00020\u0006HÆ\u0003J\t\u00106\u001a\u00020\u0006HÆ\u0003J\t\u00107\u001a\u00020\u0006HÆ\u0003J\t\u00108\u001a\u00020\u0006HÆ\u0003J©\u0001\u00109\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\b\b\u0002\u0010\b\u001a\u00020\u00062\b\b\u0002\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\u00062\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\u00102\b\b\u0002\u0010\u0011\u001a\u00020\u00062\b\b\u0002\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u00062\b\b\u0002\u0010\u0014\u001a\u00020\u0006HÆ\u0001J\u0013\u0010:\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010=\u001a\u00020\u0006HÖ\u0001J\t\u0010>\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u000e\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0017R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0017R\u0011\u0010\u000f\u001a\u00020\u0010¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0014\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0017R\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001eR\u0011\u0010\t\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0017R\u0011\u0010\u000b\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0017R\u0011\u0010\u0013\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b$\u0010\u0017R\u0011\u0010\u0012\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0017R\u0011\u0010\u0011\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017R\u0011\u0010\f\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b'\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0017¨\u0006?"}, d2 = {"Lcom/meharenterprises/originsms/ui/ChatTheme;", "", "name", "", "description", "appBackground", "", "appBarBg", "incomingBubble", "outgoingBubble", "incomingTextColor", "outgoingTextColor", "textPrimary", "textSecondary", "accentColor", "bubbleCornerRadius", "", "statusBarColor", "sentBubbleDrawable", "recvBubbleDrawable", "chatBackgroundDrawable", "(Ljava/lang/String;Ljava/lang/String;IIIIIIIIIFIIII)V", "getAccentColor", "()I", "getAppBackground", "getAppBarBg", "getBubbleCornerRadius", "()F", "getChatBackgroundDrawable", "getDescription", "()Ljava/lang/String;", "getIncomingBubble", "getIncomingTextColor", "getName", "getOutgoingBubble", "getOutgoingTextColor", "getRecvBubbleDrawable", "getSentBubbleDrawable", "getStatusBarColor", "getTextPrimary", "getTextSecondary", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes3.dex */
public final /* data */ class ChatTheme {
    private final int accentColor;
    private final int appBackground;
    private final int appBarBg;
    private final float bubbleCornerRadius;
    private final int chatBackgroundDrawable;
    private final String description;
    private final int incomingBubble;
    private final int incomingTextColor;
    private final String name;
    private final int outgoingBubble;
    private final int outgoingTextColor;
    private final int recvBubbleDrawable;
    private final int sentBubbleDrawable;
    private final int statusBarColor;
    private final int textPrimary;
    private final int textSecondary;

    /* renamed from: component1, reason: from getter */
    public final String getName() {
        return this.name;
    }

    /* renamed from: component10, reason: from getter */
    public final int getTextSecondary() {
        return this.textSecondary;
    }

    /* renamed from: component11, reason: from getter */
    public final int getAccentColor() {
        return this.accentColor;
    }

    /* renamed from: component12, reason: from getter */
    public final float getBubbleCornerRadius() {
        return this.bubbleCornerRadius;
    }

    /* renamed from: component13, reason: from getter */
    public final int getStatusBarColor() {
        return this.statusBarColor;
    }

    /* renamed from: component14, reason: from getter */
    public final int getSentBubbleDrawable() {
        return this.sentBubbleDrawable;
    }

    /* renamed from: component15, reason: from getter */
    public final int getRecvBubbleDrawable() {
        return this.recvBubbleDrawable;
    }

    /* renamed from: component16, reason: from getter */
    public final int getChatBackgroundDrawable() {
        return this.chatBackgroundDrawable;
    }

    /* renamed from: component2, reason: from getter */
    public final String getDescription() {
        return this.description;
    }

    /* renamed from: component3, reason: from getter */
    public final int getAppBackground() {
        return this.appBackground;
    }

    /* renamed from: component4, reason: from getter */
    public final int getAppBarBg() {
        return this.appBarBg;
    }

    /* renamed from: component5, reason: from getter */
    public final int getIncomingBubble() {
        return this.incomingBubble;
    }

    /* renamed from: component6, reason: from getter */
    public final int getOutgoingBubble() {
        return this.outgoingBubble;
    }

    /* renamed from: component7, reason: from getter */
    public final int getIncomingTextColor() {
        return this.incomingTextColor;
    }

    /* renamed from: component8, reason: from getter */
    public final int getOutgoingTextColor() {
        return this.outgoingTextColor;
    }

    /* renamed from: component9, reason: from getter */
    public final int getTextPrimary() {
        return this.textPrimary;
    }

    public final ChatTheme copy(String name, String description, int appBackground, int appBarBg, int incomingBubble, int outgoingBubble, int incomingTextColor, int outgoingTextColor, int textPrimary, int textSecondary, int accentColor, float bubbleCornerRadius, int statusBarColor, int sentBubbleDrawable, int recvBubbleDrawable, int chatBackgroundDrawable) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        return new ChatTheme(name, description, appBackground, appBarBg, incomingBubble, outgoingBubble, incomingTextColor, outgoingTextColor, textPrimary, textSecondary, accentColor, bubbleCornerRadius, statusBarColor, sentBubbleDrawable, recvBubbleDrawable, chatBackgroundDrawable);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChatTheme)) {
            return false;
        }
        ChatTheme chatTheme = (ChatTheme) other;
        return Intrinsics.areEqual(this.name, chatTheme.name) && Intrinsics.areEqual(this.description, chatTheme.description) && this.appBackground == chatTheme.appBackground && this.appBarBg == chatTheme.appBarBg && this.incomingBubble == chatTheme.incomingBubble && this.outgoingBubble == chatTheme.outgoingBubble && this.incomingTextColor == chatTheme.incomingTextColor && this.outgoingTextColor == chatTheme.outgoingTextColor && this.textPrimary == chatTheme.textPrimary && this.textSecondary == chatTheme.textSecondary && this.accentColor == chatTheme.accentColor && Float.compare(this.bubbleCornerRadius, chatTheme.bubbleCornerRadius) == 0 && this.statusBarColor == chatTheme.statusBarColor && this.sentBubbleDrawable == chatTheme.sentBubbleDrawable && this.recvBubbleDrawable == chatTheme.recvBubbleDrawable && this.chatBackgroundDrawable == chatTheme.chatBackgroundDrawable;
    }

    public int hashCode() {
        return (((((((((((((((((((((((((((((this.name.hashCode() * 31) + this.description.hashCode()) * 31) + Integer.hashCode(this.appBackground)) * 31) + Integer.hashCode(this.appBarBg)) * 31) + Integer.hashCode(this.incomingBubble)) * 31) + Integer.hashCode(this.outgoingBubble)) * 31) + Integer.hashCode(this.incomingTextColor)) * 31) + Integer.hashCode(this.outgoingTextColor)) * 31) + Integer.hashCode(this.textPrimary)) * 31) + Integer.hashCode(this.textSecondary)) * 31) + Integer.hashCode(this.accentColor)) * 31) + Float.hashCode(this.bubbleCornerRadius)) * 31) + Integer.hashCode(this.statusBarColor)) * 31) + Integer.hashCode(this.sentBubbleDrawable)) * 31) + Integer.hashCode(this.recvBubbleDrawable)) * 31) + Integer.hashCode(this.chatBackgroundDrawable);
    }

    public String toString() {
        return "ChatTheme(name=" + this.name + ", description=" + this.description + ", appBackground=" + this.appBackground + ", appBarBg=" + this.appBarBg + ", incomingBubble=" + this.incomingBubble + ", outgoingBubble=" + this.outgoingBubble + ", incomingTextColor=" + this.incomingTextColor + ", outgoingTextColor=" + this.outgoingTextColor + ", textPrimary=" + this.textPrimary + ", textSecondary=" + this.textSecondary + ", accentColor=" + this.accentColor + ", bubbleCornerRadius=" + this.bubbleCornerRadius + ", statusBarColor=" + this.statusBarColor + ", sentBubbleDrawable=" + this.sentBubbleDrawable + ", recvBubbleDrawable=" + this.recvBubbleDrawable + ", chatBackgroundDrawable=" + this.chatBackgroundDrawable + ")";
    }

    public ChatTheme(String name, String description, int appBackground, int appBarBg, int incomingBubble, int outgoingBubble, int incomingTextColor, int outgoingTextColor, int textPrimary, int textSecondary, int accentColor, float bubbleCornerRadius, int statusBarColor, int sentBubbleDrawable, int recvBubbleDrawable, int chatBackgroundDrawable) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(description, "description");
        this.name = name;
        this.description = description;
        this.appBackground = appBackground;
        this.appBarBg = appBarBg;
        this.incomingBubble = incomingBubble;
        this.outgoingBubble = outgoingBubble;
        this.incomingTextColor = incomingTextColor;
        this.outgoingTextColor = outgoingTextColor;
        this.textPrimary = textPrimary;
        this.textSecondary = textSecondary;
        this.accentColor = accentColor;
        this.bubbleCornerRadius = bubbleCornerRadius;
        this.statusBarColor = statusBarColor;
        this.sentBubbleDrawable = sentBubbleDrawable;
        this.recvBubbleDrawable = recvBubbleDrawable;
        this.chatBackgroundDrawable = chatBackgroundDrawable;
    }

    public final String getName() {
        return this.name;
    }

    public final String getDescription() {
        return this.description;
    }

    public final int getAppBackground() {
        return this.appBackground;
    }

    public final int getAppBarBg() {
        return this.appBarBg;
    }

    public final int getIncomingBubble() {
        return this.incomingBubble;
    }

    public final int getOutgoingBubble() {
        return this.outgoingBubble;
    }

    public final int getIncomingTextColor() {
        return this.incomingTextColor;
    }

    public final int getOutgoingTextColor() {
        return this.outgoingTextColor;
    }

    public final int getTextPrimary() {
        return this.textPrimary;
    }

    public final int getTextSecondary() {
        return this.textSecondary;
    }

    public final int getAccentColor() {
        return this.accentColor;
    }

    public final float getBubbleCornerRadius() {
        return this.bubbleCornerRadius;
    }

    public final int getStatusBarColor() {
        return this.statusBarColor;
    }

    public final int getSentBubbleDrawable() {
        return this.sentBubbleDrawable;
    }

    public final int getRecvBubbleDrawable() {
        return this.recvBubbleDrawable;
    }

    public final int getChatBackgroundDrawable() {
        return this.chatBackgroundDrawable;
    }
}
