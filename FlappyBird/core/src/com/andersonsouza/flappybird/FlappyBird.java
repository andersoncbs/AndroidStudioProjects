package com.andersonsouza.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {


    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;

    //configurações
    private int larguraDispositivo;
    private int alturaDispositivo;
    private int alturaMaximaPassaro;
    private Random numeroRandomico;

    private float variacao = 0;
    private float velocidadeQueda = 0;
    private float posicaoInicialVertical = 0;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaEntreCanosRandomica;

    //constantes
    private static final int AUMENTO_VELOCIDADE_QUEDA = -15;
    private static final int POSICAO_INICIAL_X_PASSARO = 120;
    private static final int ESPACO_ENTRE_CANOS = 300;
    private static final int VELOCIDADE_ROLAGEM = 300;
    private static final int VARIACAO_ENTRE_CANOS = 800;

    @Override
	public void create () {
//        Gdx.app.log("Create", "Inicializado");
        batch = new SpriteBatch();
        numeroRandomico = new Random();
        numeroRandomico.setSeed(System.currentTimeMillis());

        passaros = new Texture[3];
        passaros[0] = new Texture("passaro1.png");
        passaros[1] = new Texture("passaro2.png");
        passaros[2] = new Texture("passaro3.png");

        fundo  = new Texture("fundo.png");
        canoBaixo = new Texture("cano_baixo.png");
        canoTopo = new Texture("cano_topo.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();
        posicaoInicialVertical = alturaDispositivo / 2;
        alturaMaximaPassaro = alturaDispositivo - 50;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = ESPACO_ENTRE_CANOS;
	}

	@Override
	public void render () {
        deltaTime = Gdx.graphics.getDeltaTime();

        variacao += deltaTime * 10;
        posicaoMovimentoCanoHorizontal -= deltaTime * VELOCIDADE_ROLAGEM;
        velocidadeQueda++;

        if (variacao > passaros.length - 1) {
            variacao = 0;
        }

        if (Gdx.input.justTouched()) {
            velocidadeQueda = AUMENTO_VELOCIDADE_QUEDA;
        }

        //verifica se o pássaro está dentro dos limites
        if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
            posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
            if (posicaoInicialVertical < 0) {
                posicaoInicialVertical = 0;
            } else if (posicaoInicialVertical > alturaMaximaPassaro) {
                posicaoInicialVertical = alturaMaximaPassaro;
            }
        }

        //verifica se o cano saiu inteiramente da tela
        if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
            posicaoMovimentoCanoHorizontal = larguraDispositivo;

            alturaEntreCanosRandomica = numeroRandomico.nextInt(VARIACAO_ENTRE_CANOS) - VARIACAO_ENTRE_CANOS / 2;
        }

        batch.begin();

        //a ordem dos draw's indica a profundidade, o 1º é o mais ao fundo
        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal,
                alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal,
                alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica);
        batch.draw(passaros[(int) variacao], POSICAO_INICIAL_X_PASSARO, posicaoInicialVertical);

        batch.end();
	}
	
	@Override
	public void dispose () {
	}
}
