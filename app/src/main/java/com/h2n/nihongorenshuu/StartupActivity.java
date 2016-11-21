package com.h2n.nihongorenshuu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.h2n.nihongorenshuu.app.transquiz.TransQuizHome;
import com.h2n.nihongorenshuu.entity.Grammar;
import com.h2n.nihongorenshuu.repo.GrammarRepo;
import com.h2n.nihongorenshuu.repo.SelectedHistoryRepo;

import java.util.List;

/**
 * Created by ninhh on 11/21/2016.
 */

public class StartupActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SelectedHistoryRepo shr = new SelectedHistoryRepo();
        List<Integer> list =  shr.getAllSelectedGrammar();
        if(list != null && list.size() != 0) {
            GrammarRepo gr = new GrammarRepo();
            Grammar gra = gr.getGrammarById(list.get(0));
            int level = gra.getLevel();

            Intent i = new Intent(this, TransQuizHome.class);
            Bundle b = new Bundle();
            b.putInt("level", level);
            i.putExtras(b);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        } else {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

}
