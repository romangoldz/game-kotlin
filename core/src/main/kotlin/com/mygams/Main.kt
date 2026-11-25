package com.mygams

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer


class Main : ApplicationAdapter() {
    lateinit var map: TiledMap
    lateinit var camera: OrthographicCamera
    lateinit var renderMap: OrthogonalTiledMapRenderer
    
    override fun create() {
        camera = OrthographicCamera()
        camera.setToOrtho(false, 800f, 400f)
        
        map = TmxMapLoader().load("suka.tmx")
        renderMap = OrthogonalTiledMapRenderer(map,1f)
        
    }
    
    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        
        camera.update()
        renderMap.view = camera
        renderMap.render()
    }
    
    override fun dispose() {
        map.dispose()
        renderMap.dispose()
    }
}