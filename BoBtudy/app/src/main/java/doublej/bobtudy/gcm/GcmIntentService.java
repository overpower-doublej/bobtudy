/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package doublej.bobtudy.gcm;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import doublej.bobtudy.R;
import doublej.bobtudy.UI.BoBRoomApplyVote.ApplyVoteBoBtudyActivity;
import doublej.bobtudy.UI.CurrentBoBRoom.CurrentBoBroom;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
    private static final int CODE_ACCESS_JOIN = 0;
    private static final int CODE_VOTE_FINISH = 1;
    private static final int CODE_NEW_CHAT = 2;

    public static final int NOTIFICATION_ID = 7147;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }

    public static final String TAG = "GCM Demo";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                // sendNotification("Send error: " + extras.toString());
                // Do nothing
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                // sendNotification("Deleted messages on server: "
                // + extras.toString());
                // Do nothing

            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                // If it's a regular GCM message, do some work.
                /*
                 * @@@ REGULAR GCM MESSAGE HANDLER
				 */
                // Post notification of received message.
                sendNotification(extras);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(Bundle extras) {
        int code = extras.getInt("CODE");
        String dataJsonString = extras.getString("data");
        try {
            JSONObject data = new JSONObject(dataJsonString);

            if (code == CODE_ACCESS_JOIN) {
                String userId = data.getString("userId");
                String accessId = data.getString("accessId");
                String postId = data.getString("postId");

                mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent intent = new Intent(this, ApplyVoteBoBtudyActivity.class);
                intent.putExtra("userId", userId);
                intent.putExtra("accessId", accessId);
                intent.putExtra("postId", postId);
                intent.putExtra("nid", NOTIFICATION_ID);

                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        this).setSmallIcon(R.drawable.ic_stat_gcm).setContentTitle("밥터디 참가 신청")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("title 2!"))
                        .setContentText(userId);

                mBuilder.setContentIntent(contentIntent);
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            } else if (code == CODE_NEW_CHAT) {

            } else if (code == CODE_VOTE_FINISH) {
                String accessId = data.getString("accessId");
                String postId = data.getString("postId");

                mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

                Intent intent = new Intent(this, ApplyVoteBoBtudyActivity.class);
                intent.putExtra("accessId", accessId);
                intent.putExtra("postId", postId);
                intent.putExtra("nid", NOTIFICATION_ID);

                PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        this).setSmallIcon(R.drawable.ic_stat_gcm).setContentTitle("밥터디 참가 신청 결과")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(""))
                        .setContentText("결과는?!");

                mBuilder.setContentIntent(contentIntent);
                mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
