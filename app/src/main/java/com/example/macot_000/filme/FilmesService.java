package com.example.macot_000.filme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maiko on 07/05/16.
 */
public class FilmesService {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public FilmesService(Context context) {
        banco = new CriaBanco(context);
    }

    public boolean salvar(Filme filme) {
        ContentValues valores;
        long resultado = -1;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(CriaBanco.NOME, filme.getNome());
        valores.put(CriaBanco.GENERO, filme.getGenero());

        if (filme.getId() != null && filme.getId() != 0){
            String where = CriaBanco.ID + " = " + filme.getId();
            resultado = db.update(CriaBanco.TABELA, valores, where, null);
        } else {
            resultado = db.insert(CriaBanco.TABELA, null, valores);
        }

        db.close();
        return resultado != -1;
    }

    public boolean remover(Integer id){
        String where = CriaBanco.ID + " = " + id;
        db = banco.getReadableDatabase();
        int resultado = db.delete(CriaBanco.TABELA, where, null);
        db.close();
        return resultado != -1;
    }

    public List<Filme> buscar(){
        Cursor dados;
        List<Filme> filmes = new ArrayList<>();
        String[] campos =  {CriaBanco.ID,CriaBanco.NOME, CriaBanco.GENERO};

        db = banco.getReadableDatabase();
        dados = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(dados!=null && dados.moveToFirst()){
            do {
                filmes.add(new Filme(dados.getInt(0), dados.getString(1), dados.getString(2)));
            } while (dados.moveToNext());
        }

        db.close();
        return filmes;
    }
}
