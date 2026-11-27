package com.mygams

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.ScreenUtils

class TileMap(public val game: Game) : Screen {
    private lateinit var camera: OrthographicCamera
    private lateinit var map: TiledMap
    private lateinit var renderer: OrthogonalTiledMapRenderer
    
    override fun show() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, 700f, 420f)
        
        // Загрузка карты — ок, но в идеале юзай AssetManager, 
        // чтобы не ловить фризы при загрузке тяжелого дерьма
        map = TmxMapLoader().load("tilemap/map.tmx")
        renderer = OrthogonalTiledMapRenderer(map, 1f)
    }
    
    // Вот этот метод — батя, всё пишем сюда
    override fun render(delta: Float) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1f)
        
        camera.update()
        renderer.view = camera // Правильнее юзать setView(camera)
        renderer.render()
        
        if (Gdx.input.justTouched()) {
            // Переходим на другой экран
            game.screen = UI(game) 
            // Dispose лучше вызывать осознанно. 
            // Если этот экран тебе больше не всрался — удаляй.
            this.dispose() 
        }
    }

    override fun resize(width: Int, height: Int) {
        // Камеру бы обновить при ресайзе, а то всё растянет к хренам
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }

    override fun dispose() {
        // Красава, ресурсы надо чистить, тут без базара
        renderer.dispose()
        map.dispose()
    }
    
    // Пустые методы просто оставляй пустыми, не надо туда TODO пихать
    override fun pause() {}
    override fun resume() {}
    override fun hide() {}
}