<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/bg_surface"
    android:orientation="vertical">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize">

        <com.google.android.material.appbar.MaterialToolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/origin_primary"
            android:theme="@style/Theme.OriginSMS"
            app:navigationIcon="@drawable/ic_back"
            app:titleTextColor="@android:color/white" />

        <LinearLayout
            android:id="@+id/messageSelectionBar"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:background="@color/origin_primary_dark"
            android:gravity="center_vertical"
            android:orientation="horizontal"
            android:paddingHorizontal="@dimen/spacing_sm"
            android:visibility="gone"
            tools:visibility="visible">

            <ImageButton
                android:id="@+id/btnCloseMessageSelection"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:background="?attr/selectableItemBackgroundBorderless"
                android:contentDescription="@string/action_settings"
                android:src="@drawable/ic_close_white" />

            <TextView
                android:id="@+id/txtMessageSelectionCount"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginStart="@dimen/spacing_md"
                android:layout_weight="1"
                android:textColor="@android:color/white"
                android:textSize="18sp"
                tools:text="1" />

            <ImageButton
                android:id="@+id/btnMessageCopy"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:background="?attr/selectableItemBackgroundBorderless"
                android:contentDescription="@string/action_copy_text"
                android:src="@drawable/ic_copy" />

            <ImageButton
                android:id="@+id/btnMessageForward"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:background="?attr/selectableItemBackgroundBorderless"
                android:contentDescription="@string/action_forward"
                android:src="@drawable/ic_forward" />

            <ImageButton
                android:id="@+id/btnMessageDelete"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:background="?attr/selectableItemBackgroundBorderless"
                android:contentDescription="@string/action_delete_message"
                android:src="@drawable/ic_delete" />

        </LinearLayout>

    </FrameLayout>

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerMessages"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:clipToPadding="false"
        android:paddingHorizontal="@dimen/spacing_sm"
        android:paddingVertical="@dimen/spacing_md"
        tools:listitem="@layout/item_message_sent" />

    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/divider" />

    <HorizontalScrollView
        android:id="@+id/attachmentPreviewScroll"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/surface_card"
        android:scrollbars="none"
        android:visibility="gone">

        <LinearLayout
            android:id="@+id/attachmentPreviewContainer"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:padding="@dimen/spacing_sm" />

    </HorizontalScrollView>

    <LinearLayout
        android:id="@+id/simIndicatorRow"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/surface_card"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:paddingHorizontal="@dimen/spacing_md"
        android:paddingVertical="4dp"
        android:visibility="gone">

        <ImageView
            android:layout_width="16dp"
            android:layout_height="16dp"
            android:contentDescription="@null"
            android:src="@drawable/ic_sim_card"
            tools:ignore="ContentDescription" />

        <TextView
            android:id="@+id/txtSimLabel"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginStart="6dp"
            android:layout_weight="1"
            android:textColor="@color/text_secondary"
            android:textSize="12sp"
            tools:text="Sending from SIM 1 · Jazz" />

    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/surface_card"
        android:gravity="bottom"
        android:orientation="horizontal"
        android:padding="@dimen/spacing_sm">

        <ImageButton
            android:id="@+id/btnAttach"
            android:layout_width="44dp"
            android:layout_height="44dp"
            android:layout_gravity="center_vertical"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="@string/action_attach"
            android:src="@drawable/ic_attach" />

        <ImageButton
            android:id="@+id/btnEmoji"
            android:layout_width="44dp"
            android:layout_height="44dp"
            android:layout_gravity="center_vertical"
            android:background="?attr/selectableItemBackgroundBorderless"
            android:contentDescription="@string/action_emoji"
            android:src="@drawable/ic_emoji" />

        <EditText
            android:id="@+id/editMessage"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_gravity="center_vertical"
            android:layout_marginHorizontal="@dimen/spacing_sm"
            android:layout_weight="1"
            android:autofillHints=""
            android:background="@drawable/bg_input_field"
            android:hint="@string/hint_type_message"
            android:inputType="textMultiLine|textCapSentences|textAutoCorrect"
            android:maxLines="5"
            android:paddingHorizontal="@dimen/spacing_md"
            android:paddingVertical="@dimen/spacing_sm"
            android:textColor="@color/text_primary"
            android:textColorHint="@color/text_secondary"
            android:textSize="15sp" />

        <ImageButton
            android:id="@+id/btnSend"
            android:layout_width="44dp"
            android:layout_height="44dp"
            android:layout_gravity="center_vertical"
            android:background="@drawable/bg_send_button"
            android:contentDescription="@string/action_send"
            android:src="@drawable/ic_send" />

    </LinearLayout>

    <androidx.emoji2.emojipicker.EmojiPickerView
        android:id="@+id/emojiPickerView"
        android:layout_width="match_parent"
        android:layout_height="280dp"
        android:background="@color/surface_card"
        android:visibility="gone" />

</LinearLayout>
