package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils

class Main : ApplicationAdapter() {

    private lateinit var map: TiledMap
    private lateinit var renderer: OrthogonalTiledMapRenderer
    private lateinit var camera: OrthographicCamera
    private lateinit var batch: SpriteBatch
    private lateinit var playerTex: Texture

    private val playerPos = Vector2(5f, 5f)
    private val unitScale = 1f / 32f // тайл 32x32 px → 1 юніт = 1 тайл

    override fun create() {
        map = TmxMapLoader().load("tilemap/map.tmx")
        renderer = OrthogonalTiledMapRenderer(map, unitScale)

        camera = OrthographicCamera().apply {
            setToOrtho(false, 20f, 15f) // видно 20x15 тайлів
        }

        batch = SpriteBatch()
        playerTex = Texture("player.png")
    }

    override fun render() {
        ScreenUtils.clear(0.2f, 0.2f, 0.3f, 1f)

        handleInput()

        camera.position.set(playerPos.x, playerPos.y, 0f)
        camera.update()

        renderer.setView(camera)
        renderer.render()

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(playerTex, playerPos.x - 0.5f, playerPos.y - 0.5f, 1f, 1f)
        batch.end()
    }

    private fun handleInput() {
        val speed = 0.1f
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) playerPos.x += speed
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))  playerPos.x -= speed
        if (Gdx.input.isKeyPressed(Input.Keys.UP))    playerPos.y += speed
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))  playerPos.y -= speed
    }

    override fun dispose() {
        map.dispose()
        renderer.dispose()
        batch.dispose()
        playerTex.dispose()
    }
}