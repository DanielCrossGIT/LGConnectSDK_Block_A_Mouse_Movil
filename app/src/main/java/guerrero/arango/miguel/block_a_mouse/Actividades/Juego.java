package guerrero.arango.miguel.block_a_mouse.Actividades;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.connectsdk.service.capability.listeners.ResponseListener;
import com.connectsdk.service.command.ServiceCommandError;

import java.util.ArrayList;

import guerrero.arango.miguel.block_a_mouse.Adapters.BloquesAdapter;
import guerrero.arango.miguel.block_a_mouse.Dialogs.FinalizadoDialog;
import guerrero.arango.miguel.block_a_mouse.JsonHelper;
import guerrero.arango.miguel.block_a_mouse.Models.Bloque;
import guerrero.arango.miguel.block_a_mouse.R;
import guerrero.arango.miguel.block_a_mouse.Singletons.AlertSingleton;
import guerrero.arango.miguel.block_a_mouse.Singletons.ProgressSingleton;
import guerrero.arango.miguel.block_a_mouse.Singletons.VariablesGlobales;

/**
 * Created by Miguel on 05/10/2016.
 */

public class Juego extends AppCompatActivity{

    ImageView ivIzquierda,ivArriba,ivAbajo,ivDerecha, ivHorizontal,ivVertical,ivPersonaje,ivImagen;

    String tipo = "ficha";
    ProgressDialog progressDialog;

    FrameLayout contenedorCuadrado;
    LinearLayout contenedorRectangulo1, contenedorRectangulo2;

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;

    FinalizadoDialog finalizadoDialog = new FinalizadoDialog();

    ArrayList<Bloque> bloques = new ArrayList<>();
    int avatar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        VariablesGlobales.getInstance().setJuego(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        contenedorCuadrado = (FrameLayout) findViewById(R.id.contenedorCuadrado);
        contenedorRectangulo1 = (LinearLayout) findViewById(R.id.contenedorRectangulo1);
        contenedorRectangulo2 = (LinearLayout) findViewById(R.id.contenedorRectangulo2);


        progressDialog = new ProgressDialog(Juego.this);
        progressDialog.setMessage("Esperando turno...");
        progressDialog.setCancelable(false);

        if(getIntent().getBooleanExtra("turno",false)){
            progressDialog.cancel();
        }   else{
            progressDialog.show();
        }

        avatar = VariablesGlobales.getInstance().getAvatar();




        ivIzquierda = (ImageView) findViewById(R.id.ivIzquierda);
        ivIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "izquierda"), new ResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                    }

                    @Override
                    public void onError(ServiceCommandError error) {
                        AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                    }
                });

            }
        });

        ivArriba = (ImageView) findViewById(R.id.ivArriba);
        ivArriba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "arriba"), new ResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                    }

                    @Override
                    public void onError(ServiceCommandError error) {
                        AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                    }
                });

            }
        });

        ivAbajo = (ImageView) findViewById(R.id.ivAbajo);
        ivAbajo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "abajo"), new ResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                    }

                    @Override
                    public void onError(ServiceCommandError error) {
                        AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                    }
                });

            }
        });

        ivDerecha = (ImageView) findViewById(R.id.ivDerecha);
        ivDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "derecha"), new ResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                    }

                    @Override
                    public void onError(ServiceCommandError error) {
                        AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                    }
                });

            }
        });

        ivHorizontal = (ImageView) findViewById(R.id.ivHorizontal);
        ivHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(bloques.size() > 0){
                    ivImagen.setImageResource(R.drawable.boton_bloque_horizontal);
                    tipo ="bloqueH";

                    VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "seleccion"), new ResponseListener<Object>() {
                        @Override
                        public void onSuccess(Object object) {
                        }

                        @Override
                        public void onError(ServiceCommandError error) {
                            AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                        }
                    });
                }   else{
                    AlertSingleton.getInstance(Juego.this, "No cuenta con bloques disponibles").dialog.show();
                }


            }
        });

        ivVertical = (ImageView) findViewById(R.id.ivVertical);
        ivVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bloques.size() > 0){
                    ivImagen.setImageResource(R.drawable.boton_bloque_vertical);
                    tipo ="bloqueV";

                    VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "seleccion"), new ResponseListener<Object>() {
                        @Override
                        public void onSuccess(Object object) {
                        }

                        @Override
                        public void onError(ServiceCommandError error) {
                            AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                        }
                    });
                }   else{
                    AlertSingleton.getInstance(Juego.this, "No cuenta con bloques disponibles").dialog.show();
                }


            }
        });

        ivPersonaje = (ImageView) findViewById(R.id.ivPersonaje);
        ivPersonaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (avatar){
                    case 1:
                        ivImagen.setImageResource(R.drawable.cabeza1);

                        break;
                    case 2:
                        ivImagen.setImageResource(R.drawable.cabeza2);
                        break;
                    case 3:
                        ivImagen.setImageResource(R.drawable.cabeza3);
                        break;
                    case 4:
                        ivImagen.setImageResource(R.drawable.cabeza4);
                        break;
                    default:
                        ivImagen.setImageResource(R.drawable.cabeza5);
                        break;
                }
                tipo ="ficha";

                VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento(tipo, "seleccion"), new ResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object object) {
                    }

                    @Override
                    public void onError(ServiceCommandError error) {
                        AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                    }
                });

            }
        });

        ivImagen = (ImageView) findViewById(R.id.ivImagen);
        ivImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.enviarEvento("posicionarBloque", ""), new ResponseListener<Object>() {
                    @Override
                    public void onSuccess(Object object) {


                    }

                    @Override
                    public void onError(ServiceCommandError error) {
                        AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();
                    }
                });

            }
        });

        switch (avatar){
            case 1:
                ivPersonaje.setImageResource(R.drawable.cabeza1);
                ivImagen.setImageResource(R.drawable.cabeza1);
                contenedorCuadrado.setBackgroundResource(R.drawable.jugador1_contenedor_cuadrado);
                contenedorRectangulo1.setBackgroundResource(R.drawable.jugador1_contenedor_rectangulo);
                contenedorRectangulo2.setBackgroundResource(R.drawable.jugador1_contenedor_rectangulo);
                break;
            case 2:
                ivPersonaje.setImageResource(R.drawable.cabeza2);
                ivImagen.setImageResource(R.drawable.cabeza2);
                contenedorCuadrado.setBackgroundResource(R.drawable.jugador2_contenedor_cuadrado);
                contenedorRectangulo1.setBackgroundResource(R.drawable.jugador2_contenedor_rectangulo);
                contenedorRectangulo2.setBackgroundResource(R.drawable.jugador2_contenedor_rectangulo);
                break;
            case 3:
                ivPersonaje.setImageResource(R.drawable.cabeza3);
                ivImagen.setImageResource(R.drawable.cabeza3);
                contenedorCuadrado.setBackgroundResource(R.drawable.jugador3_contenedor_cuadrado);
                contenedorRectangulo1.setBackgroundResource(R.drawable.jugador3_contenedor_rectangulo);
                contenedorRectangulo2.setBackgroundResource(R.drawable.jugador3_contenedor_rectangulo);
                break;
            case 4:
                ivPersonaje.setImageResource(R.drawable.cabeza4);
                ivImagen.setImageResource(R.drawable.cabeza4);
                contenedorCuadrado.setBackgroundResource(R.drawable.jugador4_contenedor_cuadrado);
                contenedorRectangulo1.setBackgroundResource(R.drawable.jugador4_contenedor_rectangulo);
                contenedorRectangulo2.setBackgroundResource(R.drawable.jugador4_contenedor_rectangulo);
                break;
            default:
                ivPersonaje.setImageResource(R.drawable.cabeza5);
                ivImagen.setImageResource(R.drawable.cabeza5);
                break;
        }
        Bloque newBloque = new Bloque();

        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);
        bloques.add(newBloque);

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.rv);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        lManager = new GridLayoutManager(this,3);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new BloquesAdapter(bloques);
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VariablesGlobales.getInstance().setJuego(null);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(Juego.this)
                .setTitle("Abandonar la partida")
                .setMessage("Estas seguro que deseas salir del juego?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        VariablesGlobales.getInstance().getWebAppSession().sendMessage(JsonHelper.close(), new ResponseListener<Object>() {
                            @Override
                            public void onSuccess(Object object) {
                                finish();
                            }

                            @Override
                            public void onError(ServiceCommandError error) {
                                ProgressSingleton.getInstance(Juego.this).pDialog.cancel();
                                AlertSingleton.getInstance(Juego.this,error.getMessage()).dialog.show();

                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    public void bloquearMando(){
        progressDialog.show();

    }
    public void desbloquearMando(){
        progressDialog.cancel();

        switch (avatar){
            case 1:
                ivImagen.setImageResource(R.drawable.cabeza1);
                break;
            case 2:
                ivImagen.setImageResource(R.drawable.cabeza2);
                break;
            case 3:
                ivImagen.setImageResource(R.drawable.cabeza3);
                break;
            case 4:
                ivImagen.setImageResource(R.drawable.cabeza4);
                break;
            default:
                ivImagen.setImageResource(R.drawable.cabeza5);
                break;
        }
        tipo ="ficha";
    }

    public void juegoCancelado(){
        finish();
    }

    public void JuegoFinalizado(){


        finalizadoDialog.show(getSupportFragmentManager(),null);

    }

    public void disminuirBloque(){
        try{
            bloques.remove(0);
            adapter.notifyItemRemoved(0);

        }   catch (Exception e){
            e.printStackTrace();

        }
    }

    public void reiniciarJuego(){
        finalizadoDialog.dismiss();
        if(bloques.size() < 10){
            while (bloques.size() < 10){
                Bloque newBloque = new Bloque();
                bloques.add(newBloque);
            }
            adapter.notifyDataSetChanged();
        }
    }
}
