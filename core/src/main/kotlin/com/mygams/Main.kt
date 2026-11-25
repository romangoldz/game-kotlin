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
    private lateinit var playerCamera: PlayerCamera
    private lateinit var batch: SpriteBatch
    private lateinit var playerTex: Texture
    private lateinit var collision: CollisionMap

    private val playerPos = Vector2(5f, 5f)
    private val unitScale = 1f / 32f

    override fun create() {
        map = TmxMapLoader().load("tilemap/map.tmx")
        renderer = OrthogonalTiledMapRenderer(map, unitScale)

        camera = OrthographicCamera().apply {
            setToOrtho(false, 20f, 15f)
        }

        val w = map.properties.get("width", Int::class.java)
        val h = map.properties.get("height", Int::class.java)
        playerCamera = PlayerCamera(camera, w, h, 32, 32)
        collision = CollisionMap(map, 32, 32)

        batch = SpriteBatch()
        playerTex = Texture("player.png")

        // Nearest для піксель-арту
        map.tileSets.getTileSet(0).region.texture.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        )
    }

    override fun render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f)

        movePlayer()

        playerCamera.update(playerPos.x, playerPos.y)

        renderer.setView(camera)
        renderer.render()

        batch.projectionMatrix = camera.combined
        batch.begin()
        batch.draw(playerTex, playerPos.x - 0.5f, playerPos.y - 0.5f, 1f, 1f)
        batch.end()
    }

    private fun movePlayer() {
        val step = 0.1f
        val dx = when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> step
            Gdx.input.isKeyPressed(Input.Keys.LEFT)  -> -step
            else -> 0f
        }
        val dy = when {
            Gdx.input.isKeyPressed(Input.Keys.UP)   -> step
            Gdx.input.isKeyPressed(Input.Keys.DOWN) -> -step
            else -> 0f
        }

        // перевіряємо колізії по осях окремо
        if (dx != 0f && !collision.isBlocked(playerPos.x + dx, playerPos.y)) {
            playerPos.x += dx
        }
        if (dy != 0f && !collision.isBlocked(playerPos.x, playerPos.y + dy)) {
            playerPos.y += dy
        }
    }

    override fun dispose() {
        map.dispose()
        renderer.dispose()
        batch.dispose()
        playerTex.dispose()
    }
}