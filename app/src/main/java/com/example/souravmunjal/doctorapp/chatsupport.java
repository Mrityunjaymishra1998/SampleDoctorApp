package com.example.souravmunjal.doctorapp;

import android.content.Context;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ai.api.AIDataService;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class chatsupport extends AppCompatActivity implements
        TextToSpeech.OnInitListener {

    final AIConfiguration config = new AIConfiguration("68b41ce8c8834dd0ba2594c8d4465f9f",
            AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.System);

    String speech;
    final AIDataService aiDataService = new AIDataService(config);

    final AIRequest aiRequest = new AIRequest();

    Context context = this;

    private TextToSpeech tts;

    AIASyncTask aiasyncTask = new AIASyncTask();
    EditText chat;
    ImageView send;
    List<Message> messageList;
    RecyclerView mMessageRecycler;
    MessageListAdapter mMessageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN, android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_chatsupport);
        ImageView b=(ImageView) findViewById(R.id.back2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         chat=(EditText) findViewById(R.id.editText9);
         send=(ImageView) findViewById(R.id.imageView12);
        tts = new TextToSpeech(this, this);
        messageList=new ArrayList<>();
        mMessageRecycler=(RecyclerView) findViewById(R.id.recyclerview);
        mMessageAdapter=new MessageListAdapter(this,messageList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMessageRecycler.setLayoutManager(linearLayoutManager);
        mMessageRecycler.setAdapter(mMessageAdapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aiasyncTask = new AIASyncTask();

                aiRequest.setQuery(chat.getText().toString());

//                tv.append("\nYou:  "+et.getText().toString()+"\n");

                messageList.add(new Message(Message.TYPE_USER, chat.getText().toString()));
                mMessageAdapter.notifyDataSetChanged();


                aiasyncTask.execute(aiRequest);

                chat.setText("");
            }
        });

    }


    class AIASyncTask extends AsyncTask<AIRequest, Void, AIResponse> {


        @Override
        protected AIResponse doInBackground(AIRequest... requests) {
            final AIRequest request = requests[0];
            try {
                final AIResponse response = aiDataService.request(aiRequest);
                return response;
            } catch (AIServiceException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(AIResponse aiResponse) {
            if (aiResponse != null) {
                // process aiResponse here

                Log.d("Hello", "onPostExecute: " + aiResponse.toString());

                final Result result = aiResponse.getResult();
                 speech = result.getFulfillment().getSpeech();
                speakOut();
                messageList.add(new Message(Message.TYPE_DOC, speech));
                mMessageAdapter.notifyDataSetChanged();

            }
        }

    };
    public class MessageListAdapter extends RecyclerView.Adapter {

        private Context mContext;
        private List<Message> mMessageList;

        public MessageListAdapter(Context context, List<Message> messageList) {
            mContext = context;
            mMessageList = messageList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            if (viewType == Message.TYPE_DOC) {

                Log.d("AT00", "onCreateViewHolder: ");
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.received, parent, false);
                return new ReceivedMessageHolder(view);
            } else if (viewType == Message.TYPE_USER) {

                Log.d("AT01", "onCreateViewHolder: ");

                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.sent, parent, false);
                return new SentMessageHolder(view);
            }


            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            Message message = mMessageList.get(position);

            switch (holder.getItemViewType()) {
                case Message.TYPE_USER:
                    Log.d("AT00", "onCreateViewHolder: ");

                    ((SentMessageHolder) holder).bind(message);
                    break;
                case Message.TYPE_DOC:
                    Log.d("AT00", "onCreateViewHolder: ");

                    ((ReceivedMessageHolder) holder).bind(message);
                    break;
            }


        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        public int getItemViewType(int position) {
            Message message = mMessageList.get(position);

            if (message.getUserType() == Message.TYPE_DOC) {
                return Message.TYPE_DOC;
            } else {
                return Message.TYPE_USER;
            }
        }

        private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText, nameText;
            ImageView profileImage;

            ReceivedMessageHolder(View itemView) {
                super(itemView);

                messageText = (TextView) itemView.findViewById(R.id.text_message_bodyr);
                nameText = (TextView) itemView.findViewById(R.id.text_message_namer);
                profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
            }

            void bind(Message message) {
                messageText.setText(message.getMessage());

                // Format the stored timestamp into a readable String using method.
//                timeText.setText(Utils.formatDateTime(message.getCreatedAt()));

                nameText.setText("Doctor");

                // Insert the profile image from the URL into the ImageView.
            }
        }

        private class SentMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText, nameText;
            ImageView profileImage;

            SentMessageHolder(View itemView) {
                super(itemView);

                messageText = (TextView) itemView.findViewById(R.id.text_message_body);
                //        nameText = (TextView) itemView.findViewById(R.id.text_message_name);
            }

            void bind(Message message) {
                messageText.setText(message.getMessage());

                // Format the stored timestamp into a readable String using method.
//                timeText.setText(Utils.formatDateTime(message.getCreatedAt()));

//                nameText.setText("You");
                // Insert the profile image from the URL into the ImageView.
            }
        }
        }
    @Override
    public void onDestroy() {
// Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }

        } else {
            Log.e("TTS", "Initilization Failed!");
        }

    }

    private void speakOut() {
        tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }


}
