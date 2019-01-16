package com.andersonsouza.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {


    private SpriteBatch batch;
    private Texture[] passaros;
    private Texture fundo;
    private Texture canoBaixo;
    private Texture canoTopo;
    private Texture gameOver;
    private BitmapFont textoPontos;
    private BitmapFont mensagem;
    private Circle passaroCirculo;
    private Rectangle retanguloCanoTopo;
    private Rectangle retanguloCanoBaixo;
    private ShapeRenderer shape;


    //configurações
    private float larguraDispositivo;
    private float alturaDispositivo;
    private float alturaMaximaPassaro;
    private Random numeroRandomico;
    private int estadoJogo = 0;
    private Integer pontuacao = 0;

    private float variacao = 0;
    private float velocidadeQueda = 0;
    private float posicaoInicialVertical = 0;
    private float posicaoMovimentoCanoHorizontal;
    private float espacoEntreCanos;
    private float deltaTime;
    private float alturaEntreCanosRandomica;
    private boolean marcouPonto = false;

    //configurações da câmera
    private OrthographicCamera camera;
    private Viewport viewport;
    private final float VIRTUAL_WIDTH = 768;
    private final float VIRTUAL_HEIGHT = 1024;

    //constantes
    private static final int AUMENTO_VELOCIDADE_QUEDA = -15;
    private static final int POSICAO_INICIAL_X_PASSARO = 120;
    private static final int ESPACO_ENTRE_CANOS = 300;
    private static final int VELOCIDADE_ROLAGEM = 300;
    private static final int VARIACAO_ENTRE_CANOS = 800;
    private static final boolean DESENHAR_SHAPES = false;

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
        gameOver = new Texture("game_over.png");

        //configurações da câmera
        camera = new OrthographicCamera();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);

        textoPontos = new BitmapFont();
        textoPontos.setColor(Color.GOLD);
        textoPontos.getData().setScale(6);

        mensagem = new BitmapFont();
        mensagem.setColor(Color.WHITE);
        mensagem.getData().setScale(3);

        passaroCirculo = new Circle();
        shape = new ShapeRenderer();

//        larguraDispositivo = Gdx.graphics.getWidth();
//        alturaDispositivo = Gdx.graphics.getHeight();

        larguraDispositivo = VIRTUAL_WIDTH;
        alturaDispositivo = VIRTUAL_HEIGHT;

        posicaoInicialVertical = alturaDispositivo / 2;
        alturaMaximaPassaro = alturaDispositivo - 50;
        posicaoMovimentoCanoHorizontal = larguraDispositivo;
        espacoEntreCanos = ESPACO_ENTRE_CANOS;
	}

	@Override
	public void render () {
        camera.update();

        //limpar frames anteriores
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        deltaTime = Gdx.graphics.getDeltaTime();
        variacao += deltaTime * 10;
        if (variacao > passaros.length - 1) {
            variacao = 0;
        }

        if (estadoJogo == 0) {
            if (Gdx.input.justTouched()) {
                estadoJogo = 1;
            }
        } else {
            velocidadeQueda++;
            //verifica se o pássaro está dentro dos limites
            if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;
                if (posicaoInicialVertical < 0) {
                    posicaoInicialVertical = 0;
                } else if (posicaoInicialVertical > alturaMaximaPassaro) {
                    posicaoInicialVertical = alturaMaximaPassaro;
                }
            }

            if (estadoJogo == 1) {
                posicaoMovimentoCanoHorizontal -= deltaTime * VELOCIDADE_ROLAGEM;
                if (Gdx.input.justTouched()) {
                    velocidadeQueda = AUMENTO_VELOCIDADE_QUEDA;
                }

                //verifica se o cano saiu inteiramente da tela
                if (posicaoMovimentoCanoHorizontal < -canoTopo.getWidth()) {
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;

                    alturaEntreCanosRandomica = numeroRandomico.nextInt(VARIACAO_ENTRE_CANOS) - VARIACAO_ENTRE_CANOS / 2;
                    marcouPonto = false;
                }

                //pontuacao
                if (posicaoMovimentoCanoHorizontal < POSICAO_INICIAL_X_PASSARO && !marcouPonto) {
                    pontuacao++;
                    marcouPonto = true;
                }
                //game over
            } else {
                if (Gdx.input.justTouched()) {
                    estadoJogo = 0;
                    pontuacao = 0;
                    velocidadeQueda = 0;
                    posicaoInicialVertical = alturaDispositivo / 2;
                    posicaoMovimentoCanoHorizontal = larguraDispositivo;
                }
            }

        }

        batch.begin();
        batch.setProjectionMatrix(camera.combined);

        //a ordem dos draw's indica a profundidade, o 1º é o mais ao fundo
        float yCanoTopo = alturaDispositivo / 2 + espacoEntreCanos / 2 + alturaEntreCanosRandomica;
        float yCanoBaixo = alturaDispositivo / 2 - canoBaixo.getHeight() - espacoEntreCanos / 2 + alturaEntreCanosRandomica;

        batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
        batch.draw(canoTopo, posicaoMovimentoCanoHorizontal, yCanoTopo);
        batch.draw(canoBaixo, posicaoMovimentoCanoHorizontal, yCanoBaixo);
        batch.draw(passaros[(int) variacao], POSICAO_INICIAL_X_PASSARO, posicaoInicialVertical);
        textoPontos.draw(batch, pontuacao.toString(), larguraDispositivo / 2, alturaDispositivo - 50);

        if (estadoJogo == 2) {
            batch.draw(gameOver, larguraDispositivo / 2 - gameOver.getWidth() / 2, alturaDispositivo / 2);
            mensagem.draw(batch, "Toque para reiniciar", larguraDispositivo / 2 - 200, alturaDispositivo / 2 - gameOver.getHeight() / 2);
        }

        batch.end();

        //formas
        passaroCirculo.set(
                POSICAO_INICIAL_X_PASSARO + passaros[0].getWidth() / 2,
                posicaoInicialVertical + passaros[0].getHeight() / 2,
                passaros[0].getWidth() / 2);
        retanguloCanoBaixo = new Rectangle(posicaoMovimentoCanoHorizontal, yCanoBaixo, canoBaixo.getWidth(), canoBaixo.getHeight());
        retanguloCanoTopo = new Rectangle(posicaoMovimentoCanoHorizontal, yCanoTopo, canoTopo.getWidth(), canoTopo.getHeight());

        if (DESENHAR_SHAPES) {
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.circle(passaroCirculo.x, passaroCirculo.y, passaroCirculo.radius);
            shape.rect(retanguloCanoTopo.x, retanguloCanoTopo.y, retanguloCanoTopo.width, retanguloCanoTopo.height);
            shape.rect(retanguloCanoBaixo.x, retanguloCanoBaixo.y, retanguloCanoBaixo.width, retanguloCanoBaixo.height);
            shape.setColor(Color.RED);
            shape.end();
        }

        //teste colisão
        if (Intersector.overlaps(passaroCirculo, retanguloCanoBaixo)
                || Intersector.overlaps(passaroCirculo, retanguloCanoTopo)
                || posicaoInicialVertical <= 0) {
            estadoJogo = 2;
        }

	}

	@Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
	
	@Override
	public void dispose () {

	}
}

