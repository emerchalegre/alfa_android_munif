package com.example.macot_000.filme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class FilmeActivity extends AppCompatActivity {

    private FilmesService filmesService;

    private TextView edFilme;
    private TextView edGenero;

    private ListView listView;

    private List<Filme> filmes;

    private boolean isEditandoFilme;
    private Filme filmeSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        filmesService = new FilmesService(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filme);

        edFilme = (TextView) findViewById(R.id.edFilme);
        edGenero = (TextView) findViewById(R.id.edGenero);

        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);

        buscarDados();


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listView) {
            getMenuInflater().inflate(R.menu.menu_edicao, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        filmeSelecionado = filmes.get(info.position);

        switch (item.getItemId()) {
            case R.id.menu_editar_item:
                editarFilme();
                return true;
            case R.id.menu_remover_item:
                removerAluno();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void removerAluno() {
        if (filmesService.remover(filmeSelecionado.getId())) {
            buscarDados();
            imprimeMensagem("Aluno removido com sucesso !");
        }
    }

    private void editarFilme() {
        isEditandoFilme = true;
        preencherCampos(filmeSelecionado);
    }

    private void preencherCampos(Filme filme) {
        edFilme.setText(filme.getNome());
        edGenero.setText(filme.getGenero());
    }

    public void save(View view) {

        if (!isEditandoFilme) filmeSelecionado = new Filme();

        filmeSelecionado.setNome(edFilme.getText().toString());
        filmeSelecionado.setGenero(edGenero.getText().toString());

        boolean isSalvou = filmesService.salvar(filmeSelecionado);

        if (isSalvou) {
            imprimeMensagem("Registro salvo com sucesso !");
            limparCampos();
            buscarDados();
        } else {
            imprimeMensagem("Não foi possível salvar o registro !");
        }
    }

    private void imprimeMensagem(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private void buscarDados() {

        filmes = filmesService.buscar();

        listView.setAdapter(new ArrayAdapter<>(FilmeActivity.
                this, android.R.layout.simple_list_item_1, filmes));
    }

    private void limparCampos() {
        edFilme.setText("");
        edGenero.setText("");
        edFilme.requestFocus();
    }

}
