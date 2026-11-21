package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.ScreenViewport

class Main : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    private lateinit var image: Texture
    private lateinit var music1: Music
    private lateinit var stage: Stage
    private lateinit var skin: Skin

    override fun create() {
        batch = SpriteBatch()
        image = Texture("libgdx.png")
        music1 = Gdx.audio.newMusic(Gdx.files.internal("music/music1.mp3")).apply {
            isLooping = true
            volume = 0.5f
            play()
        }
        stage = Stage(ScreenViewport())
        Gdx.input.inputProcessor = stage
        
        skin = Skin(Gdx.files.internal("ui/uiskin.json"))
        
        val button = TextButton ("Кнопка!", skin)
        button.setPosition(100f, 100f)
        button.setSize(200f, 60f)
        
        stage.addActor(button)
    }

    override fun render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f)

        batch.begin()
        batch.draw(image, 140f, 210f)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        image.dispose()
        music1.dispose()
        stage.dispose()
        skin.dispose()
    }
}
