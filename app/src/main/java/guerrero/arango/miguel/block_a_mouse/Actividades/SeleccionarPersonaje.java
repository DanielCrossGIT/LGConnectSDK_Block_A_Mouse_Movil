package guerrero.arango.miguel.block_a_mouse.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.connectsdk.service.capability.listeners.ResponseListener;
import com.connectsdk.service.command.ServiceCommandError;

import guerrero.arango.miguel.block_a_mouse.JsonHelper;
import guerrero.arango.miguel.block_a_mouse.R;
import guerrero.arango.miguel.block_a_mouse.Singletons.AlertSingleton;
import guerrero.arango.miguel.block_a_mouse.Singletons.ProgressSingleton;
import guerrero.arango.miguel.block_a_mouse.Singletons.VariablesGlobales;

/**
 * Created by Miguel on 25/09/2016.
 */

public class SeleccionarPersonaje extends AppCompatActivity {
    Button bConectar;
    AppCompatEditText etNombre;
    ImageView ivIzquierda,ivDerecha,ivPersonaje;
    int positionSelected;
    String nombre = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_personaje);

        //getSupportActionBar().setTitle("Bienvenido al Juego");

        positionSelected = 1;

        ivIzquierda = (ImageView) findViewById(R.id.ivIzquierda);
        ivIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionSelected < 2){
                    positionSelected = 4;
                }   else{
                    positionSelected--;
                }

                switch (positionSelected){
                    case 1:
                        ivPersonaje.setImageResource(R.drawable.cabeza1);
                        break;
                    case 2:
                        ivPersonaje.setImageResource(R.drawable.cabeza2);
                        break;
                    case 3:
                        ivPersonaje.setImageResource(R.drawable.cabeza3);
                        break;
                    case 4:
                        ivPersonaje.setImageResource(R.drawable.cabeza4);
                        break;
                }
            }
        });

        ivDerecha = (ImageView) findViewById(R.id.ivDerecha);
        ivDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(positionSelected > 3){
                    positionSelected = 1;
                }   else{
                    positionSelected++;
                }

                switch (positionSelected){
                    case 1:
                        ivPersonaje.setImageResource(R.drawable.cabeza1);
                        break;
                    case 2:
                        ivPersonaje.setImageResource(R.drawable.cabeza2);
                        break;
                    case 3:
                        ivPersonaje.setImageResource(R.drawable.cabeza3);
                        break;
                    case 4:
                        ivPersonaje.setImageResource(R.drawable.cabeza4);
                        break;
                }

            }
        });

        ivPersonaje = (ImageView) findViewById(R.id.ivPersonaje);


        VariablesGlobales.getInstance().setSeleccionarPersonaje(this);




        etNombre = (AppCompatEditText) findViewById(R.id.etNombre);
        etNombre.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        bConectar = (Button) findViewById(R.id.bConectar);
        bConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentarConectar();
            }
        });
    }

    private void intentarConectar(){
        etNombre.setError(null);
        String nombre = etNombre.getText().toString();
        boolean cancelar = false;
        View focusView = null;

        if(nombre.length() < 3){
            etNombre.setError("Debe tener almenos 3 caracteres");
            focusView = etNombre;
            cancelar = true;
        }

        if(cancelar){
            focusView.requestFocus();
        }   else{
            conectar(nombre.toUpperCase(), positionSelected);
        }
    }

    private void conectar(String nombre, int avatar){
        ProgressSingleton.getInstance(SeleccionarPersonaje.this).pDialog.show();
        this.nombre = nombre;

        VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.ConnectPlayer(nombre, avatar), new ResponseListener<Object>() {
            @Override
            public void onSuccess(Object object) {
                //ProgressSingleton.getInstance(SeleccionarPersonaje.this).pDialog.cancel();


            }
            @Override
            public void onError(ServiceCommandError error) {
                ProgressSingleton.getInstance(SeleccionarPersonaje.this).pDialog.cancel();
                AlertSingleton.getInstance(SeleccionarPersonaje.this, error.getMessage()).dialog.show();

            }
        });
        //Toast.makeText(this,"Nombre validado",Toast.LENGTH_SHORT).show();
    }

    public void NextStep(boolean resultado){
        ProgressSingleton.getInstance(SeleccionarPersonaje.this).pDialog.cancel();
        Intent intent = new Intent(this, EsperaJugadores.class);
        intent.putExtra("resultado",resultado);

        VariablesGlobales.getInstance().setAvatar(positionSelected);
        intent.putExtra("nombre",nombre);
        startActivity(intent);

    }

    public void NoNextStep(){
        ProgressSingleton.getInstance(SeleccionarPersonaje.this).pDialog.cancel();
    }



}
