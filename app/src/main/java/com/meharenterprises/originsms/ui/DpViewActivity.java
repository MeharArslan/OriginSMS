package com.meharenterprises.originsms.ui;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.meharenterprises.originsms.R;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DpViewActivity.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0014¨\u0006\u0007"}, d2 = {"Lcom/meharenterprises/originsms/ui/DpViewActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class DpViewActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp_view);
        String photoUri = getIntent().getStringExtra("photoUri");
        String displayName = getIntent().getStringExtra("displayName");
        if (displayName == null) {
            displayName = "";
        }
        ImageView imgDp = (ImageView) findViewById(R.id.imgDpFull);
        TextView txtName = (TextView) findViewById(R.id.txtDpName);
        ImageView btnClose = (ImageView) findViewById(R.id.btnDpClose);
        txtName.setText(displayName);
        btnClose.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.DpViewActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DpViewActivity.onCreate$lambda$0(DpViewActivity.this, view);
            }
        });
        imgDp.setOnClickListener(new View.OnClickListener() { // from class: com.meharenterprises.originsms.ui.DpViewActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                DpViewActivity.onCreate$lambda$1(DpViewActivity.this, view);
            }
        });
        if (photoUri != null) {
            try {
                imgDp.setImageURI(Uri.parse(photoUri));
                return;
            } catch (Exception e) {
                imgDp.setImageResource(R.drawable.ic_person);
                return;
            }
        }
        imgDp.setImageResource(R.drawable.ic_person);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$0(DpViewActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void onCreate$lambda$1(DpViewActivity this$0, View it) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.finish();
    }
}
