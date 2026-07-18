package com.meharenterprises.originsms.ui.compose;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwnerKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;

/* compiled from: ShareTargetActivity.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\n\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0002J\u0012\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014¨\u0006\n"}, d2 = {"Lcom/meharenterprises/originsms/ui/compose/ShareTargetActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "extractDestinationNumber", "", "extractSharedText", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ShareTargetActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String destinationNumber = extractDestinationNumber();
        String sharedText = extractSharedText();
        String str = destinationNumber;
        if (!(str == null || StringsKt.isBlank(str))) {
            BuildersKt__Builders_commonKt.launch$default(LifecycleOwnerKt.getLifecycleScope(this), null, null, new ShareTargetActivity$onCreate$1(this, destinationNumber, sharedText, null), 3, null);
            return;
        }
        Intent intent = new Intent(this, (Class<?>) ComposeActivity.class);
        String str2 = sharedText;
        if (!(str2 == null || StringsKt.isBlank(str2))) {
            intent.putExtra("extra_prefill_body", sharedText);
        }
        startActivity(intent);
        finish();
    }

    private final String extractDestinationNumber() {
        String schemeSpecific;
        String it;
        Uri data = getIntent().getData();
        if (data == null || (schemeSpecific = data.getSchemeSpecificPart()) == null || (it = (String) CollectionsKt.firstOrNull(StringsKt.split$default((CharSequence) schemeSpecific, new String[]{";"}, false, 0, 6, (Object) null))) == null || !(!StringsKt.isBlank(it))) {
            return null;
        }
        return it;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final String extractSharedText() {
        String action = getIntent().getAction();
        if (action != null) {
            switch (action.hashCode()) {
                case -1173264947:
                    if (action.equals("android.intent.action.SEND")) {
                        return getIntent().getStringExtra("android.intent.extra.TEXT");
                    }
                    break;
                case 2068787464:
                    if (action.equals("android.intent.action.SENDTO")) {
                        return getIntent().getStringExtra("android.intent.extra.TEXT");
                    }
                    break;
            }
        }
        return null;
    }
}
