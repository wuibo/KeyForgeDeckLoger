package com.luma.keyforgedeckloger;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ImageView;
import android.widget.Button;
import android.view.MenuItem;
import android.widget.TableRow;
import android.widget.TextView;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;


public class  MainActivity extends Activity implements View.OnClickListener{

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private static int RC_SIGN_IN = 1000;

    final private int marte = 1;
    final private int brobnar = 2;
    final private int logos = 3;
    final private int dis = 4;
    final private int indomita = 5;
    final private int sombras = 6;
    final private int sanctun = 7;

    private float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //google
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build();
        // Build a GoogleSignInClient with the options specified by gso
        System.out.println("ENTRE GOOGLE");
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setOnClickListener(this);
        scale = this.getResources().getDisplayMetrics().density;
    }

    //Método OnStart
    @Override
    protected void onStart(){
        System.out.println("Main: onStart");
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            //ya hay cuenta
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            TextView view = (TextView)findViewById(R.id.Name);
            view.setText(account.getDisplayName());
        }else{
            //no hay cuenta
            findViewById(R.id.Name).setVisibility(View.GONE);
            findViewById(R.id.sign_out_button).setVisibility(View.GONE);
        }
        //prueba carga mazo
        int dim = (int) getResources().getDimension(R.dimen.casa);
        //Layout para la línea
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dim,dim);
        LinearLayout linea = new LinearLayout(this);
        linea.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout plantilla = findViewById(R.id.decks_layout);
        linea.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        plantilla.addView(linea);
        //Imágenes de casas
        ImageView img1 = new ImageView(this);
        img1.setBackgroundResource(R.drawable.brobnar);
        img1.setLayoutParams(params);
        linea.addView(img1);
        ImageView img2 = new ImageView(this);
        img2.setBackgroundResource(R.drawable.marte);
        img2.setLayoutParams(params);
        linea.addView(img2);
        ImageView img3 = new ImageView(this);
        img3.setBackgroundResource(R.drawable.sanctun);
        img3.setLayoutParams(params);
        linea.addView(img3);
        //Nombre dle mazo
        TextView txt = new TextView(this);
        txt.setText("Titulo del mazo para segunda lína probando");
        txt.setMaxWidth((int) getResources().getDimension(R.dimen.deck_name_max));
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.deck_name_tam));
        linea.addView(txt);
        //espacio para alinear votones
        View space = new View(this);
        LinearLayout.LayoutParams spaceLP = new LinearLayout.LayoutParams(0,1,1.0f);
        space.setLayoutParams(spaceLP);
        linea.addView(space);

        //Botones de accion
        LinearLayout buttons = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = RelativeLayout.ALIGN_PARENT_RIGHT;
        params.setMargins(0,0,0,0);
        buttons.setPadding(0,0,0,0);
        buttons.setLayoutParams(params);
        buttons.setOrientation(LinearLayout.VERTICAL);
        linea.addView(buttons);

        //shapedrawable.getPaint().setStyle(Style.STROKE);



        //botones
        Button b1 = new Button(this);
        buttons.addView(b1);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.deck_buttons_width),(int) getResources().getDimension(R.dimen.deck_buttons_height));
        bp.setMargins(0,0,0,1);
        b1.setLayoutParams(bp);
        b1.setGravity(Gravity.CENTER);
        b1.setPadding(0,0,0,0);
        b1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.deck_buttons_tam));
        b1.setTextColor(getResources().getColor(R.color.keytext));
        b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.key_button));
        b1.setText("+ Match");

        b1.setBottom(0);
        Button b2 = new Button(this);
        buttons.addView(b2);
        b2.setLayoutParams(bp);
        b2.setGravity(Gravity.CENTER);
        b2.setPadding(0,0,0,1);
        b2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.deck_buttons_tam));
        b2.setTextColor(getResources().getColor(R.color.keytext));
        b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.key_button));
        b2.setText("stats");

        printDeck(logos,sombras,dis,"Señora Rita, contadora de la reserva");
        printDeck(sombras,indomita,brobnar,"El segundo mazo siemrpe es peor");


        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            // ...
        }
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // actualizar vista
                        findViewById(R.id.Name).setVisibility(View.GONE);
                        findViewById(R.id.sign_out_button).setVisibility(View.GONE);
                        findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
                        //borrad datos de la app
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("En Result");
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            System.out.println("Buen Result");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Actualizar vista
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            TextView view = (TextView)findViewById(R.id.Name);
            view.setText(account.getDisplayName());
            view.setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            System.out.println("Tras Cambios de interfaz");
            //actulizar datos

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println( "signInResult:failed code=" + e.getStatusCode());

        }
    }

    private void printDeck(int h1, int h2, int h3,String name){
        int img1,img2,img3;
        switch(h1){
            case marte:
                img1 = R.drawable.marte;
                break;
            case logos:
                img1 = R.drawable.logos;
                break;
            case sanctun:
                img1 = R.drawable.sanctun;
                break;
            case dis:
                img1 = R.drawable.dis;
                break;
            case indomita:
                img1 = R.drawable.indomita;
                break;
            case brobnar:
                img1 = R.drawable.brobnar;
                break;
            case sombras:
                img1 = R.drawable.sombras;
                break;
            default:
                img1 = R.drawable.marte;
        }
        switch(h2){
            case marte:
                img2 = R.drawable.marte;
                break;
            case logos:
                img2 = R.drawable.logos;
                break;
            case sanctun:
                img2 = R.drawable.sanctun;
                break;
            case dis:
                img2 = R.drawable.dis;
                break;
            case indomita:
                img2 = R.drawable.indomita;
                break;
            case brobnar:
                img2 = R.drawable.brobnar;
                break;
            case sombras:
                img2 = R.drawable.sombras;
                break;
            default:
                img2 = R.drawable.marte;
        }
        switch(h3){
            case marte:
                img3 = R.drawable.marte;
                break;
            case logos:
                img3 = R.drawable.logos;
                break;
            case sanctun:
                img3 = R.drawable.sanctun;
                break;
            case dis:
                img3 = R.drawable.dis;
                break;
            case indomita:
                img3 = R.drawable.indomita;
                break;
            case brobnar:
                img3 = R.drawable.brobnar;
                break;
            case sombras:
                img3 = R.drawable.sombras;
                break;
            default:
                img3 = R.drawable.marte;
        }
        int dim = (int) getResources().getDimension(R.dimen.casa);
        //Layout para la línea
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dim,dim);
        LinearLayout linea = new LinearLayout(this);
        linea.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout plantilla = findViewById(R.id.decks_layout);
        linea.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        linea.setDividerDrawable(getResources().getDrawable(R.drawable.divider_shape));
        plantilla.addView(linea);
        //Imágenes de casas
        ImageView cimg1 = new ImageView(this);
        cimg1.setBackgroundResource(img1);
        cimg1.setLayoutParams(params);
        linea.addView(cimg1);
        ImageView cimg2 = new ImageView(this);
        cimg2.setBackgroundResource(img2);
        cimg2.setLayoutParams(params);
        linea.addView(cimg2);
        ImageView cimg3 = new ImageView(this);
        cimg3.setBackgroundResource(img3);
        cimg3.setLayoutParams(params);
        linea.addView(cimg3);
        //Nombre dle mazo
        TextView txt = new TextView(this);
        txt.setText(name);
        txt.setMaxWidth((int) getResources().getDimension(R.dimen.deck_name_max));
        txt.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.deck_name_tam));
        linea.addView(txt);
        //espacio para alinear votones
        View space = new View(this);
        LinearLayout.LayoutParams spaceLP = new LinearLayout.LayoutParams(0,1,1.0f);
        space.setLayoutParams(spaceLP);
        linea.addView(space);

        //Botones de accion
        LinearLayout buttons = new LinearLayout(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = RelativeLayout.ALIGN_PARENT_RIGHT;
        params.setMargins(0,0,0,0);
        buttons.setPadding(0,0,0,0);
        buttons.setLayoutParams(params);
        buttons.setOrientation(LinearLayout.VERTICAL);
        linea.addView(buttons);
/*
        ShapeDrawable shapedrawable = new ShapeDrawable();
        shapedrawable.setShape(new RectShape());
        shapedrawable.getPaint().setColor(getResources().getColor(R.color.RED));
        shapedrawable.getPaint().setStrokeWidth(10f);
        //shapedrawable.getPaint().setStyle(Style.STROKE);
*/


        //botones
        Button b1 = new Button(this);
        buttons.addView(b1);
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.deck_buttons_width),(int) getResources().getDimension(R.dimen.deck_buttons_height));
        bp.setMargins(0,0,0,1);
        b1.setLayoutParams(bp);
        b1.setGravity(Gravity.CENTER);
        b1.setPadding(0,0,0,0);
        b1.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.deck_buttons_tam));
        b1.setTextColor(getResources().getColor(R.color.keytext));
        b1.setBackgroundDrawable(getResources().getDrawable(R.drawable.key_button));
        b1.setText("+ Match");

        b1.setBottom(0);
        Button b2 = new Button(this);
        buttons.addView(b2);
        b2.setLayoutParams(bp);
        b2.setGravity(Gravity.CENTER);
        b2.setPadding(0,0,0,1);
        b2.setTextSize(TypedValue.COMPLEX_UNIT_PX,getResources().getDimension(R.dimen.deck_buttons_tam));
        b2.setTextColor(getResources().getColor(R.color.keytext));
        b2.setBackgroundDrawable(getResources().getDrawable(R.drawable.key_button));
        b2.setText("stats");
    }
}


