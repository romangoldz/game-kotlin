
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.ScreenUtils

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

    private lateinit var camera: OrthographicCamera
    private lateinit var map: TiledMap
    private lateinit var renderer: OrthogonalTiledMapRenderer

    override fun create() {
        // Камера
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 600f)

        // Завантаження карти
        map = TmxMapLoader().load("tilemap/map.tmx")

        // Створення рендерера
        renderer = OrthogonalTiledMapRenderer(map, 1f)
    }

    override fun render() {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)

        // Оновлення камери
        camera.update()

        // Передача камери рендереру
        renderer.setView(camera)

        // Малювання карти
        renderer.render()
    }

    override fun dispose() {
        renderer.dispose()
        map.dispose()
    }
}
