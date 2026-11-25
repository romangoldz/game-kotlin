package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.Gdx

class Main : ApplicationAdapter() {
    private lateinit var map: TiledMap
    private lateinit var camera: OrthographicCamera
    private lateinit var renderMap: OrthogonalTiledMapRenderer
    
    override fun create() {

        Gdx.files.local("logs.txt").writeString(
            "\n========== GAME START ==========\n",
            true
        )

        Gdx.files.local("logs.txt").writeString(
            "Loading map...\n",
            true
        )

        map = TmxMapLoader().load("tilemap/map.tmx")

        Gdx.files.local("logs.txt").writeString(
            "Map loaded successfully!\n",
            true
        )

        renderMap = OrthogonalTiledMapRenderer(map, 1f)

        Gdx.files.local("logs.txt").writeString(
            "Game initialized successfully!\n",
            true
        )
    }
    
    override fun render() {
        ScreenUtils.clear(0f, 0f, 0f, 1f) 
        
        camera.update()
        renderMap.setView(camera)
        renderMap.render()
    }
    
    override fun dispose() {
        map.dispose()
        renderMap.dispose()
    }
}