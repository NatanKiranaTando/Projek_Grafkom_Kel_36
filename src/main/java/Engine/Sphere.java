package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public class Sphere extends Circle{
    float radiusZ;
    String spherename;
    int stackCount;
    int sectorCount;
    List<Vector3f> normal = new ArrayList<>();
    int nbo;
    int choice;
    String fileobj;
    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Float> centerPoint, Float radiusX, Float radiusY, Float radiusZ,
                  int sectorCount,int stackCount, int choice) {
        super(shaderModuleDataList, vertices, color, centerPoint, radiusX, radiusY);
        this.radiusZ = radiusZ;
        this.stackCount = stackCount;
        this.sectorCount = sectorCount;
        this.choice = choice;
        if(choice == 1) {
            createBoxVertices();
        }
//        createSphere();
        else if(choice == 2) {
            setUpDisplayLists();
        }
        setupVAOVBO();
    }

    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Float> centerPoint, Float radiusX, Float radiusY, Float radiusZ,
                  int sectorCount,int stackCount, int choice, String fileobj,String spherename) {
        super(shaderModuleDataList, vertices, color, centerPoint, radiusX, radiusY);
        this.radiusZ = radiusZ;
        this.stackCount = stackCount;
        this.sectorCount = sectorCount;
        this.choice = choice;
        this.fileobj = fileobj;
        this.spherename = spherename;
        if(choice == 1) {
            createBoxVertices();
        }
//        createSphere();
        else if(choice == 2) {
            setUpDisplayLists();
        }
        setupVAOVBO();
    }
    public void setUpDisplayLists(){
        vertices.clear();
        {
            Model m = null;
            try {
                m = OBJLoader.loadModel(new File(fileobj));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Face face : m.faces){
                Vector3f n1 = m.normals.get((int) face.normal.x - 1);
                normal.add(n1);
                Vector3f v1 = m.vertices.get((int) face.vertex.x - 1);
                vertices.add(v1);
                Vector3f n2 = m.normals.get((int) face.normal.y - 1);
                normal.add(n2);
                Vector3f v2 = m.vertices.get((int) face.vertex.y - 1);
                vertices.add(v2);
                Vector3f n3 = m.normals.get((int) face.normal.z - 1);
                normal.add(n3);
                Vector3f v3 = m.vertices.get((int) face.vertex.z - 1);
                vertices.add(v3);
            }
        }
    }

    public void createBox(){
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //TITIK 1
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 2
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 3
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 4
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 5
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 6
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 7
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 8
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();

        vertices.clear();
        //kotak yg sisi belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        //kotak yg sisi depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        //kotak yg sisi kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        //kotak yg sisi kanan
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        //kotak yg sisi atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak yg sisi bawah
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));

        normal = new ArrayList<>(Arrays.asList(
                //belakang
                new Vector3f(0.0f,0.0f,-1.0f),
                new Vector3f(0.0f,0.0f,-1.0f),
                new Vector3f(0.0f,0.0f,-1.0f),
                new Vector3f(0.0f,0.0f,-1.0f),
                //depan
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                new Vector3f(0.0f,0.0f,1.0f),
                //kiri
                new Vector3f(-1.0f,0.0f,0.0f),
                new Vector3f(-1.0f,0.0f,0.0f),
                new Vector3f(-1.0f,0.0f,0.0f),
                new Vector3f(-1.0f,0.0f,0.0f),
                //kanan
                new Vector3f(1.0f,0.0f,0.0f),
                new Vector3f(1.0f,0.0f,0.0f),
                new Vector3f(1.0f,0.0f,0.0f),
                new Vector3f(1.0f,0.0f,0.0f),
                //atas
                new Vector3f(0.0f,1.0f,0.0f),
                new Vector3f(0.0f,1.0f,0.0f),
                new Vector3f(0.0f,1.0f,0.0f),
                new Vector3f(0.0f,1.0f,0.0f),
                //bawah
                new Vector3f(0.0f,-1.0f,0.0f),
                new Vector3f(0.0f,-1.0f,0.0f),
                new Vector3f(0.0f,-1.0f,0.0f),
                new Vector3f(0.0f,-1.0f,0.0f)
        ));
    }
    public void createBoxVertices()
    {
        System.out.println("code");
        vertices.clear();
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //Titik 1 kiri atas belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 2 kiri bawah belakang
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 3 kanan bawah belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 4 kanan atas belakang
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) - radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 5 kiri atas depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 6 kiri bawah depan
        temp.x = centerPoint.get(0) - radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 7 kanan bawah depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) - radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();
        //Titik 8 kanan atas depan
        temp.x = centerPoint.get(0) + radiusX / 2;
        temp.y = centerPoint.get(1) + radiusY / 2;
        temp.z = centerPoint.get(2) + radiusZ / 2;
        tempVertices.add(temp);
        temp = new Vector3f();

        //kotak belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(0));
        //kotak depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(4));
        //kotak samping kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(4));

        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak samping kanan
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));

        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(7));
        //kotak bawah
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));

        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(1));
        //kotak atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));

        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(3));

        normal = new ArrayList<>(Arrays.asList(
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),
                new Vector3f(0.0f,  0.0f, -1.0f),

                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),
                new Vector3f(0.0f,  0.0f,  1.0f),

                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),
                new Vector3f(-1.0f,  0.0f,  0.0f),

                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),
                new Vector3f(1.0f,  0.0f,  0.0f),

                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f( 0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),
                new Vector3f(0.0f, -1.0f,  0.0f),

                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f),
                new Vector3f(0.0f,  1.0f,  0.0f)
        ));
    }
    public void setupVAOVBO(){
        super.setupVAOVBO();

        //set nbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normal),
                GL_STATIC_DRAW);

//        uniformsMap.createUniform("lightColor");
//        uniformsMap.createUniform("lightPos");

    }



    public void drawSetup(Camera camera, Projection projection){
        super.drawSetup(camera,projection);

        // Bind VBO
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3,
                GL_FLOAT,
                false,
                0, 0);

        if(spherename == "pesawat") {
            uniformsMap.setUniform("dirLight.direction", new Vector3f(3.2f, -1.0f, 0.3f));
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
            uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.5f, 0.5f, 0.5f));
            uniformsMap.setUniform("dirLight.specular", new Vector3f(0.0f, 0.0f, 0.0f));
//            System.out.println(centerPoint.get(0)+" " + centerPoint.get(1)+ " " + centerPoint.get(2));
            Vector3f[] _pointLightPositions = {
                    new Vector3f(centerPoint.get(0)-1.2f, centerPoint.get(1)+6,  centerPoint.get(2)-28),
                    new Vector3f(centerPoint.get(0)-1.2f, centerPoint.get(1)+6,  centerPoint.get(2)-27),
                    new Vector3f(centerPoint.get(0)-1.2f,centerPoint.get(1)+6,  centerPoint.get(2)-26),
                    new Vector3f(centerPoint.get(0)-1.2f, centerPoint.get(1)+6,  centerPoint.get(2)-26),
                    new Vector3f(centerPoint.get(0)-1.2f, centerPoint.get(1)+6,  centerPoint.get(2)-26)

//            new Vector3f(0.7f, -10.2f, centerPoint.get(2) + 20.0f),
//            new Vector3f(0.7f, -10.2f, centerPoint.get(2) + 10.0f),
//            new Vector3f(0.7f, -10.2f, centerPoint.get(2) + 0.0f),
//            new Vector3f(0.7f, -10.2f, centerPoint.get(2) - 10.0f),
//            new Vector3f(0.7f, -10.2f, centerPoint.get(2) - 20.0f),
            };
            for (int i = 0; i < _pointLightPositions.length; i++) {
                uniformsMap.setUniform("pointLights[" + i + "].position", _pointLightPositions[i]);
                uniformsMap.setUniform("pointLights[" + i + "].ambient", new Vector3f(0.02f, 0.000f, 0.000f));
                uniformsMap.setUniform("pointLights[" + i + "].diffuse", new Vector3f(0.03f, 0.0f, 0.0f));
                uniformsMap.setUniform("pointLights[" + i + "].specular", new Vector3f(0.0f, 0.0f, 0.0f));
                uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
                uniformsMap.setUniform("pointLights[" + i + "].linear", 0.09f);
                uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0.032f);
            }
        }else if(spherename == "kota"){
            uniformsMap.setUniform("dirLight.direction", new Vector3f(3.2f, -0.3f, 0.3f));
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
            uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.5f, 0.5f, 0.5f));
            uniformsMap.setUniform("dirLight.specular", new Vector3f(0.0f, 0.0f, 0.0f));
            //posisi pointLight
            Vector3f[] _pointLightPositions = {
                    new Vector3f(0.7f, 0.2f, 2.0f),
                    new Vector3f(2.3f, -3.3f, -4.0f),
                    new Vector3f(-4.0f, 2.0f, -12.0f),
                    new Vector3f(-4.0f, 2.0f, -12.0f),
                    new Vector3f(0.0f, 0.0f, -3.0f)

//                    new Vector3f(0.1f, 0.0f, 0.0f),
//                    new Vector3f(0.0f, 0.0f, 0.0f),
//                    new Vector3f(0.0f, 0.0f, 0.0f),
//                    new Vector3f(0.0f, 0.0f, 0.0f),
//                    new Vector3f(0.0f, 0.0f, 0.0f),
            };
            for (int i = 0; i < _pointLightPositions.length; i++) {
                uniformsMap.setUniform("pointLights[" + i + "].position", _pointLightPositions[i]);
                uniformsMap.setUniform("pointLights[" + i + "].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
                uniformsMap.setUniform("pointLights[" + i + "].diffuse", new Vector3f(0.5f, 0.5f, 0.5f));
                uniformsMap.setUniform("pointLights[" + i + "].specular", new Vector3f(0.0f, 0.0f, 0.0f));
                uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
                uniformsMap.setUniform("pointLights[" + i + "].linear", 0.09f);
                uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0.032f);
            }
        }else if(spherename == "gedung"){
            uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f, -0.4f, -0.3f));
            uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
            uniformsMap.setUniform("dirLight.diffuse", new Vector3f(.7f, .7f, .7f));
            uniformsMap.setUniform("dirLight.specular", new Vector3f(0.7f, 0.7f, 0.7f));
            //posisi pointLight
            Vector3f[] _pointLightPositions = {
//            new Vector3f(0.7f, 0.2f, 2.0f),
//            new Vector3f(2.3f, -3.3f, -4.0f),
//            new Vector3f(-4.0f, 2.0f, -12.0f),
//            new Vector3f(-4.0f, 2.0f, -12.0f),
//            new Vector3f(0.0f, 0.0f, -3.0f)

                    new Vector3f(-63.0f, 70.0f, -30.0f),
                    new Vector3f(-10.0f, 70.0f, -10.0f),
                    new Vector3f(-300.0f, -100070.0f, 0.0f),
                    new Vector3f(0.0f, -1000000.0f, 0.0f),
                    new Vector3f(-120.0f, -100000.0f, -130.0f),
            };
            for (int i = 0; i < _pointLightPositions.length; i++) {
                uniformsMap.setUniform("pointLights[" + i + "].position", _pointLightPositions[i]);
                uniformsMap.setUniform("pointLights[" + i + "].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
                uniformsMap.setUniform("pointLights[" + i + "].diffuse", new Vector3f(0.5f, 0.5f, 0.5f));
                uniformsMap.setUniform("pointLights[" + i + "].specular", new Vector3f(0.0f, 0.0f, 0.0f));
                uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
                uniformsMap.setUniform("pointLights[" + i + "].linear", 0.09f);
                uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0.032f);
            }
        }



//        uniformsMap.setUniform("dirLight.position",new Vector3f(-0.2f,30.0f,-0.3f));
//        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f,100.0f,0.3f));
//        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f,0.05f,0.05f));
//        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f,0.4f,0.4f));
//        uniformsMap.setUniform("dirLight.specular", new Vector3f(1.0f,1.0f,1.0f));
//
////            System.out.println(centerPoint);
//
//            //posisi pointLight
//            Vector3f[] _pointLightPositions = {
////            new Vector3f(0.7f, 0.2f, 2.0f),
////            new Vector3f(2.3f, -3.3f, -4.0f),
////            new Vector3f(-4.0f, 2.0f, -12.0f),
////            new Vector3f(0.0f, 0.0f, -3.0f)
//
//                    new Vector3f(0.7f, -10.2f, centerPoint.get(2) + 20.0f),
//                    new Vector3f(0.7f, -10.2f, centerPoint.get(2) + 10.0f),
//                    new Vector3f(0.7f, -10.2f, centerPoint.get(2) + 0.0f),
//                    new Vector3f(0.7f, -10.2f, centerPoint.get(2) - 10.0f),
//                    new Vector3f(0.7f, -10.2f, centerPoint.get(2) - 20.0f),
//            };
//            for (int i = 0; i < _pointLightPositions.length; i++) {
//                uniformsMap.setUniform("pointLights[" + i + "].position", _pointLightPositions[i]);
//                uniformsMap.setUniform("pointLights[" + i + "].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
//                uniformsMap.setUniform("pointLights[" + i + "].diffuse", new Vector3f(-0.8f, -0.8f, -0.8f));
//                uniformsMap.setUniform("pointLights[" + i + "].specular", new Vector3f(.0f, .0f, .0f));
//                uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
//                uniformsMap.setUniform("pointLights[" + i + "].linear", 0.09f);
//                uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0.032f);
//            }

//
        //spotlight
//        uniformsMap.setUniform("spotLight.position",camera.getPosition());
//        uniformsMap.setUniform("spotLight.direction",camera.getDirection());
//        uniformsMap.setUniform("spotLight.ambient",new Vector3f(0.0f,0.0f,0.0f));
//        uniformsMap.setUniform("spotLight.diffuse",new Vector3f(1.0f,1.0f,1.0f));
//        uniformsMap.setUniform("spotLight.specular",new Vector3f(1.0f,1.0f,1.0f));
//        uniformsMap.setUniform("spotLight.constant",1.0f);
//        uniformsMap.setUniform("spotLight.linear",0.09f);
//        uniformsMap.setUniform("spotLight.quadratic",0.032f);
//        uniformsMap.setUniform("spotLight.cutOff",(float)Math.cos(Math.toRadians(12.5f)));
//        uniformsMap.setUniform("spotLight.outerCutOff",(float)Math.cos(Math.toRadians(12.5f)));
//        uniformsMap.setUniform("viewPos",camera.getPosition());
    }
    //    public void draw(){
//        drawSetup();
//        glLineWidth(2); //ketebalan garis
//        glPointSize(2); //besar kecil vertex
//        glDrawArrays(GL_LINE_STRIP,
//                0,
//                vertices.size());
//    }
    public void createSphere(){
        float pi = (float)Math.PI;

        float sectorStep = 2 * (float)Math.PI / sectorCount;
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * (float)Math.cos(StackAngle);
            y = radiusY * (float)Math.cos(StackAngle);
            z = radiusZ * (float)Math.sin(StackAngle);

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.x = centerPoint.get(0) + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerPoint.get(1) + y * (float)Math.sin(sectorAngle);
                temp_vector.z = centerPoint.get(2) + z;
                vertices.add(temp_vector);
            }
        }
    }
}