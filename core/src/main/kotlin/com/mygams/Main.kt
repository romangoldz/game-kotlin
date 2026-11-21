
package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.audio.Music

class Main : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch
    private lateinit var image: Texture
    private lateinit var music1: Music

    override fun create() {
        batch = SpriteBatch()
        image = Texture("libgdx.png")
        music1 = Gdx.audio.newMusic(Gdx.files.internal("music/music1.mp3")).apply {
            isLooping = true
            volume = 0.5f
            play()
        }
    }

    override fun render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f)

        batch.begin()
        batch.draw(image, 140f, 210f)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        image.dispose()
        music1.dispose()
    }
}
