package com.andersonsouza.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {


    private SpriteBatch batch;
    private Texture passaro;
    private Texture fundo;

    //configurações
    private int movimento = 0;
    private int larguraDispositivo;
    private int alturaDispositivo;

    @Override
	public void create () {
//        Gdx.app.log("Create", "Inicializado");

        batch = new SpriteBatch();
        passaro = new Texture("passaro1.png");
        fundo  = new Texture("fundo.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
        movimento++;

        batch.begin();

        //a ordem dos draw's indica a profundidade, o 1º é o mais ao fundo

//        private int larguraDispositivo = Gdx.graphics.getWidth();
//        private int alturaDispositivo = Gdx.graphics.getHeight();

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(passaro, movimento, 400);

        batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
