package com.example.ajudabrasil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME = "loginDb.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_TIPO_CADASTRO = "tipo_cadastro";

    private static final String TABLE_DOACOES = "doacoes";
    private static final String COLUMN_DOACAO_ID = "doacao_id";
    private static final String COLUMN_DOACAO_USER_ID_FK = "user_id_doador";
    private static final String COLUMN_DOACAO_ONG_ID_FK = "ong_id_recebedora";
    private static final String COLUMN_DOACAO_VALOR = "valor";
    private static final String COLUMN_DOACAO_DATA = "data_doacao";
    private static final String COLUMN_DOACAO_ONG_NOME = "ong_nome_recebedora";

    // Cria a tabela de usuários
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT, "
            + COLUMN_TIPO_CADASTRO + " TEXT" + ")";

    // Cria a tabela de doacoes
    private static final String CREATE_DOACOES_TABLE = "CREATE TABLE " + TABLE_DOACOES + "("
            + COLUMN_DOACAO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_DOACAO_USER_ID_FK + " INTEGER,"
            + COLUMN_DOACAO_ONG_ID_FK + " INTEGER,"
            + COLUMN_DOACAO_VALOR + " REAL,"
            + COLUMN_DOACAO_DATA + " TEXT,"
            + COLUMN_DOACAO_ONG_NOME + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_DOACAO_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + "),"
            + "FOREIGN KEY(" + COLUMN_DOACAO_ONG_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
            + ")";
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERS_TABLE); // executa o sql de criacao da tabela
        db.execSQL(CREATE_DOACOES_TABLE); // executa sql para criar a tabela de doacoes
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS); // apaga a tabela de usuarios
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOACOES);
        onCreate(db);
    }

    public long addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase(); // escrita no banco
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_TIPO_CADASTRO, user.getTipoCadastro());

        long result = db.insert(TABLE_USERS, null, values);
        db.close();

        return result;
    }

    public long addDoacao(Doacao doacao) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DOACAO_USER_ID_FK, doacao.getUserIdDoador());
        values.put(COLUMN_DOACAO_ONG_ID_FK, doacao.getOngIdRecebedora());
        values.put(COLUMN_DOACAO_VALOR, doacao.getValor());
        values.put(COLUMN_DOACAO_DATA, doacao.getData());
        values.put(COLUMN_DOACAO_ONG_NOME, doacao.getOngNomeRecebedora());

        long result = db.insert(TABLE_DOACOES, null, values);
        db.close();
        return result;
    }

    public User checkUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase(); // somente leitura no banco
        String[] columns = {
          COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_TIPO_CADASTRO
        };

        String selection = COLUMN_USERNAME + " = ?" + " AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;
        if(cursor != null && cursor.moveToFirst()){
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_CADASTRO))
            );
            cursor.close();
        }
        db.close();
        return user;
    }

    public boolean  updatePassword(int userId, String newPassword){
        SQLiteDatabase db = this.getWritableDatabase(); // banco para escrita
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, newPassword);

        String selection =  COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        int rowsAffected =  db.update(TABLE_USERS, values, selection, selectionArgs);
        db.close();

        return rowsAffected > 0;
    }

    public List<User> getUsuariosPorTipo(String tipoCadastro){
        List<User> userList =  new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_ID, COLUMN_USERNAME, COLUMN_TIPO_CADASTRO
        };

        String selection = COLUMN_TIPO_CADASTRO + " = ?";
        String[] selectionArgs = {tipoCadastro};

        Cursor cursor = db.query(
                TABLE_USERS,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                COLUMN_USERNAME + " ASC" // odenar por nome em ordem crescente
        );

        if(cursor != null && cursor.moveToFirst()){
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIPO_CADASTRO))
                );
                userList.add(user);
                }while (cursor.moveToNext());
        }

        if(cursor != null){
            cursor.close();
        }

        db.close();
        return userList;
    }

    public List<Doacao> getDoacoesPorUsuario(int usuarioId) {
        List<Doacao> doacaoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_DOACAO_ID,
                COLUMN_DOACAO_USER_ID_FK,
                COLUMN_DOACAO_ONG_ID_FK,
                COLUMN_DOACAO_VALOR,
                COLUMN_DOACAO_ONG_NOME
        };

        String selection = COLUMN_DOACAO_USER_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(usuarioId)};

        String orderBy = COLUMN_DOACAO_DATA + " DESC";

        Cursor cursor = db.query(
                TABLE_DOACOES,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                orderBy
        );
        if (cursor != null && cursor.moveToFirst()) {
            do{
                Doacao doacao = new Doacao(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOACAO_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOACAO_USER_ID_FK)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DOACAO_ONG_ID_FK)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_DOACAO_VALOR)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOACAO_DATA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOACAO_ONG_NOME))
                );
                doacaoList.add(doacao);
            }while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return doacaoList;
    }
}
