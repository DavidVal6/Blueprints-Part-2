/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.server.PathParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetAllBlueprint() {
        try {
            Set<Blueprint> data = bps.getAllBlueprints();
            // obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{author}",method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetBlueprintsByAuthor(@PathVariable String author){
        try {
            List<Blueprint> data = bps.getBlueprintsByAuthor(author);
            if(data.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/{author}/{bpName}",method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetBlueprint(@PathVariable String author, @PathVariable String bpName){
        try {
            Blueprint data = bps.getBlueprint(author, bpName);
            if(data == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/addBp", method = RequestMethod.POST)	
    public ResponseEntity<?> manejadorPostRecursoXX(@RequestBody String data){
        try {
            if(data == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Gson gson = new Gson();
            // Analizar el JSON en un objeto Java
            BpData blueprint = gson.fromJson(data, BpData.class);
            // Acceder a los valores dentro del objeto Java
            String author = blueprint.author;
            String bpName = blueprint.bpName;
            List<Point> points = blueprint.points;
            List<List<Integer>> realPoints = new ArrayList<>();
            for (Point p: points){
                realPoints.add(Arrays.asList(p.x,p.y));
            }
            bps.addBluePrintByParameters(author, bpName, realPoints);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla", HttpStatus.CONFLICT);
        }        

    }

    class BpData {
        String author;
        String bpName;
        List<Point> points;
    }

    class Point {
        int x;
        int y;
    }

}
