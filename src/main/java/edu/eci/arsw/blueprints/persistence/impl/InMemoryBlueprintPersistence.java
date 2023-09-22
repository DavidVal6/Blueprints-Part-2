/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Component
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new ConcurrentHashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);

        Point[] pts2=new Point[]{new Point(150, 150),new Point(155, 155)};
        Blueprint bp2=new Blueprint("David", "Flor",pts2);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);

        Point[] pts3=new Point[]{new Point(30, 30),new Point(50, 50)};
        Blueprint bp3=new Blueprint("Nicolás", "La comuna de Mosquera",pts3);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        
        Point[] pts4=new Point[]{new Point(150, 150),new Point(155, 155)};
        Blueprint bp4=new Blueprint("Nicolás", "Desplazamiento Forzado",pts4);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public List<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        List<Blueprint> bluePrints = new ArrayList<Blueprint>();
        List<String> bprintname = new ArrayList<String>();
        Set<Tuple<String,String>> authors = blueprints.keySet();
        for (Tuple t : authors){
            if (t.getElem1().equals(author)){
                bprintname.add((String) t.getElem2());
            }
        }

        for (String name: bprintname){
            bluePrints.add(blueprints.get(new Tuple<>(author, name)));
        }

        return bluePrints;
    }


    @Override
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException {
        Collection<Blueprint> bluePrintsCollection = this.blueprints.values();
        Set<Blueprint> bluePrints = new HashSet<>(bluePrintsCollection);
        return bluePrints;
    }

    @Override
    public void addBluePrintByParameters(String author, String bpName, List<List<Integer>> points) throws BlueprintPersistenceException {
        List<Point> newPoints = new ArrayList<>();
        for(List<Integer> listIn : points){
            newPoints.add(new Point(listIn.get(0),listIn.get(1)));
        }
        Point[] pts=newPoints.toArray(new Point[0]);
        Blueprint bp=new Blueprint(author, bpName, pts);
        
        saveBlueprint(bp);
    }

    @Override
    public void updateBlueprint(String newAuthor, String newName, List<List<Integer>> newPoints, Blueprint bp)
            throws BlueprintNotFoundException {
        List<Point> nPoints = new ArrayList<>();
        for(List<Integer> listIn : newPoints){
            nPoints.add(new Point(listIn.get(0),listIn.get(1)));
        }
        blueprints.remove(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        bp.update(newAuthor, newName, nPoints);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
    }
    
    
}
