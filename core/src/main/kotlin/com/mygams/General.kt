package com.mygams


import com.badlogic.gdx.Game

class General : Game() {
    override fun create() {
        setScreen(TileMap(this))
    }
}