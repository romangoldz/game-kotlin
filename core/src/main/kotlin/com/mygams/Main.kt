package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.utils.ScreenUtils

class Main : ApplicationAdapter() {
    private lateinit var map: TiledMap
    private lateinit var camera: OrthographicCamera
    private lateinit var renderMap: OrthogonalTiledMapRenderer
    
    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 400f)
        
        map = TmxMapLoader().load("tilemap/map.tmx")
        renderMap = OrthogonalTiledMapRenderer(map, 1f)
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