package com.ethvotingverifier.election.check_vote;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ethvotingverifier.R;
import com.ethvotingverifier.election.ScanQrCodeActivity;
import com.ethvotingverifier.models.Election;

import org.web3j.tuples.generated.Tuple4;

public class CheckVoteActivity extends AppCompatActivity implements Election.GetVoteListener {

    EditText UUID_editText;
    public static final int REQUEST_CODE_QR_CODE = 1024;
    private final Election heliosElection = Election.instance;

    TextView hashVote, castAt, verifiedAt;
    CardView cardVote;
    ConstraintLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_check_vote);

        cardVote = findViewById(R.id.card_view_vote);
        cardVote.setVisibility(View.GONE);

        progress = findViewById(R.id.loading_info);
        progress.setVisibility(View.GONE);

        hashVote = findViewById(R.id.vote_hash);
        castAt = findViewById(R.id.cast_at);
        verifiedAt = findViewById(R.id.verified_at);

        UUID_editText = findViewById(R.id.uuid_input);
        Button checkVoteButton = findViewById(R.id.check_vote);
        Button scanQRCode = findViewById(R.id.scan_qr_code);

        checkVoteButton.setOnClickListener(v -> {
            String UUID = UUID_editText.getText().toString();
            if (!UUID.isEmpty()) {
                heliosElection.getVote(UUID, this);
                /*********** HIDE KEYBOARD ***********/
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);

                progress.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(CheckVoteActivity.this,"Please fill the UUID field", Toast.LENGTH_LONG).show();
            }
        });

        scanQRCode.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, ScanQrCodeActivity.class), REQUEST_CODE_QR_CODE);
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_QR_CODE:
                if (resultCode == RESULT_OK) {
                    String uuidFromCamera = data.getStringExtra("result");
                    UUID_editText.setText(uuidFromCamera);
                }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void receiveVoteFromBlockchain(Tuple4<String, String, String, String> vote) {

        if(vote != null) {
            /********** SET VALUES ************/
            hashVote.setText(vote.component1());
            castAt.setText(vote.component2());
            verifiedAt.setText(vote.component3());
        }
        progress.setVisibility(View.GONE);

        if(vote == null)
            Toast.makeText(this, "There was an error. Check the UUID.", Toast.LENGTH_SHORT).show();
        else
            cardVote.setVisibility(View.VISIBLE);

    }
}
