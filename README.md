# 🎨 Ray Tracing Rendering Engine

> A comprehensive 3D ray tracing rendering engine implemented in Java as part of the Introduction to Software Engineering course (ISE5784)

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white)](https://junit.org/)

---

## 📑 Table of Contents

- [About the Project](#-about-the-project)
- [Features](#-features)
- [Project Structure](#-project-structure)
- [Technologies Used](#-technologies-used)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Project](#running-the-project)
- [Usage Examples](#-usage-examples)
- [Testing](#-testing)
- [Scene Rendering Capabilities](#-scene-rendering-capabilities)
- [Architecture](#-architecture)
- [Contributors](#-contributors)
- [Course Information](#-course-information)
- [License](#-license)

---

## 🌟 About the Project

This project is a **3D Ray Tracing Rendering Engine** developed in Java. It implements advanced computer graphics algorithms to create photorealistic images by simulating the physical behavior of light. The engine supports multiple geometric primitives, various lighting models, reflection, refraction, shadows, and multi-threaded rendering for optimal performance.

The project was developed as part of the **Introduction to Software Engineering** course, demonstrating object-oriented design principles, test-driven development, and software engineering best practices.

---

## ✨ Features

### 🎯 Core Rendering Features
- ✅ **Ray Tracing Algorithm** - Full implementation of recursive ray tracing
- ✅ **Multiple Geometric Primitives** - Support for spheres, planes, triangles, polygons, cylinders, and tubes
- ✅ **Advanced Lighting Models** - Ambient, point, directional, and spot lights
- ✅ **Material Properties** - Diffuse, specular, transparency, and reflection coefficients
- ✅ **Shadows** - Realistic shadow rendering with soft shadow support
- ✅ **Reflections** - Mirror-like reflections with recursive ray tracing
- ✅ **Refractions** - Transparent material rendering with Snell's law
- ✅ **Anti-Aliasing** - Adaptive super-sampling for smoother edges

### ⚡ Performance Features
- 🚀 **Multi-threaded Rendering** - Parallel processing for faster image generation
- 🎯 **Bounding Volume Hierarchy** - Optimized intersection tests
- 📊 **Adaptive Sampling** - Intelligent pixel sampling for better quality/performance balance

### 🛠️ Development Features
- ✅ **Comprehensive Unit Tests** - Full JUnit test coverage
- ✅ **Builder Pattern** - Fluent API for camera and scene construction
- ✅ **XML Scene Loading** - External scene definition support
- ✅ **Image Export** - PNG image output

---

## 📁 Project Structure

```
ISE5784_7061_3114/
│
├── src/                          # Source code
│   ├── geometries/              # Geometric primitives and shapes
│   │   ├── Sphere.java
│   │   ├── Plane.java
│   │   ├── Triangle.java
│   │   ├── Polygon.java
│   │   ├── Cylinder.java
│   │   ├── Tube.java
│   │   ├── Geometries.java      # Composite geometry collection
│   │   ├── Intersectable.java   # Intersection interface
│   │   └── BoundingBox.java     # Bounding volume optimization
│   │
│   ├── lighting/                # Light sources and illumination
│   │   ├── Light.java           # Abstract light class
│   │   ├── AmbientLight.java    # Ambient illumination
│   │   ├── PointLight.java      # Point light source
│   │   ├── DirectionalLight.java # Directional light
│   │   ├── SpotLight.java       # Spotlight implementation
│   │   └── LightSource.java     # Light source interface
│   │
│   ├── primitives/              # Basic mathematical primitives
│   │   ├── Point.java           # 3D point
│   │   ├── Vector.java          # 3D vector
│   │   ├── Ray.java             # Ray (origin + direction)
│   │   ├── Color.java           # RGB color representation
│   │   ├── Material.java        # Material properties
│   │   ├── Double3.java         # 3D double tuple
│   │   └── Util.java            # Utility functions
│   │
│   ├── renderer/                # Rendering engine
│   │   ├── Camera.java          # Virtual camera
│   │   ├── ImageWriter.java     # Image file output
│   │   ├── RayTracerBase.java   # Abstract ray tracer
│   │   ├── SimpleRayTracer.java # Ray tracing implementation
│   │   ├── TargetArea.java      # Viewport target area
│   │   └── Pixel.java           # Pixel utilities
│   │
│   ├── scene/                   # Scene management
│   │   ├── Scene.java           # Scene container
│   │   └── XmlSceneBuilder.java # XML scene parser
│   │
│   └── test/                    # Manual testing
│       └── Main.java            # Test program
│
├── unittest/                    # JUnit test suite
│   ├── geometries/             # Geometry tests
│   ├── lighting/               # Lighting tests
│   ├── primitives/             # Primitive tests
│   └── renderer/               # Rendering tests
│       ├── RenderTests.java
│       ├── ShadowTests.java
│       ├── ReflectionRefractionTests.java
│       └── [Various scene tests]
│
├── .gitignore                  # Git ignore rules
├── ISE5784_7061_3114.iml      # IntelliJ IDEA module file
└── README.md                   # This file
```

---

## 🔧 Technologies Used

| Technology | Purpose |
|-----------|---------|
| **Java** | Core programming language |
| **JUnit 5** | Unit testing framework |
| **IntelliJ IDEA** | Integrated Development Environment |
| **Git** | Version control |

### Key Design Patterns
- 🏗️ **Builder Pattern** - Camera construction
- 🎭 **Composite Pattern** - Geometry collections
- 🔌 **Strategy Pattern** - Ray tracing algorithms
- 🏭 **Factory Pattern** - Object creation

---

## 🚀 Getting Started

### Prerequisites

Before running this project, ensure you have the following installed:

- **Java Development Kit (JDK)** 11 or higher
  ```bash
  java -version
  ```
- **IntelliJ IDEA** (recommended) or any Java IDE
- **JUnit 5** library (included in project dependencies)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/amitmoradov/ISE5784_7061_3114.git
   cd ISE5784_7061_3114
   ```

2. **Open in IntelliJ IDEA**
   - Open IntelliJ IDEA
   - Select "Open" and navigate to the project directory
   - Wait for IntelliJ to index the project

3. **Verify JUnit Setup**
   - Ensure JUnit 5 is added to the project libraries
   - The `.iml` file should already include the necessary configuration

### Running the Project

#### Option 1: Run the Test Program
```bash
# Navigate to the project directory
cd src/test

# Compile and run Main.java
javac -cp ../../ Main.java
java -cp ../../ test.Main
```

#### Option 2: Run Unit Tests
- In IntelliJ IDEA, right-click on the `unittest` folder
- Select "Run 'All Tests'"

#### Option 3: Generate a Rendered Image
- Navigate to `unittest/renderer/`
- Run any test file (e.g., `RenderTests.java`, `ShadowTests.java`)
- Images will be generated in the `images/` directory (ignored by git)

---

## 💡 Usage Examples

### Creating a Simple Scene

```java
// Create a scene
Scene scene = new Scene("My Scene")
    .setBackground(new Color(135, 206, 235)) // Sky blue
    .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

// Add geometries
scene.geometries.add(
    new Sphere(new Point(0, 0, -100), 50)
        .setEmission(new Color(255, 0, 0))
        .setMaterial(new Material()
            .setKd(0.5)
            .setKs(0.5)
            .setShininess(100)),
    
    new Plane(new Point(0, -50, 0), new Vector(0, 1, 0))
        .setEmission(new Color(50, 50, 50))
        .setMaterial(new Material()
            .setKd(0.5))
);

// Add lights
scene.lights.add(
    new PointLight(new Color(255, 255, 255), new Point(0, 50, 0))
        .setKl(0.001)
        .setKq(0.0001)
);
```

### Setting Up a Camera

```java
Camera camera = Camera.getBuilder()
    .setLocation(new Point(0, 0, 1000))
    .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
    .setVpDistance(1000)
    .setVpSize(200, 200)
    .setImageWriter(new ImageWriter("myImage", 800, 800))
    .setRayTracer(new SimpleRayTracer(scene))
    .build();

// Render the image
camera.renderImage().writeToImage();
```

### Multi-threaded Rendering

```java
camera = Camera.getBuilder()
    // ... camera configuration ...
    .setMultithreading(4) // Use 4 threads
    .build()
    .renderImage()
    .writeToImage();
```

---

## 🧪 Testing

The project includes comprehensive unit tests covering:

### Primitive Tests
- ✅ **VectorTest** - Vector operations (add, subtract, normalize, cross product, dot product)
- ✅ **PointTest** - Point operations (distance, addition with vectors)
- ✅ **RayTest** - Ray operations (finding points along rays)
- ✅ **ColorTest** - Color operations (add, scale, reduce)

### Geometry Tests
- ✅ **SphereTest** - Sphere intersection calculations
- ✅ **PlaneTest** - Plane intersection calculations
- ✅ **TriangleTest** - Triangle intersection calculations
- ✅ **PolygonTest** - Polygon validation and intersections
- ✅ **CylinderTest** - Cylinder geometry tests
- ✅ **TubeTest** - Tube geometry tests

### Rendering Tests
- ✅ **CameraTest** - Camera ray construction
- ✅ **ImageWriterTest** - Image file output
- ✅ **RenderTests** - Basic rendering pipeline
- ✅ **ShadowTests** - Shadow calculations
- ✅ **ReflectionRefractionTests** - Reflection and refraction
- ✅ **Integration Tests** - Camera-geometry integration

### Running Tests

```bash
# Run all tests
./gradlew test  # If using Gradle

# Or in IntelliJ IDEA:
# Right-click on 'unittest' folder → Run 'All Tests'
```

---

## 🎨 Scene Rendering Capabilities

The engine can render various complex scenes:

### 🏔️ Supported Scenes
- **Basic Shapes** - Spheres, triangles, planes
- **Complex Models** - Teapot, snowman, diamonds
- **Lighting Effects** - Multiple light sources, shadows, reflections
- **Transparent Objects** - Glass spheres, refractive materials
- **Special Effects** - Soft shadows, anti-aliasing, depth of field

### 📸 Example Renders
The test suite includes rendering tests for:
- `RenderTests` - Basic two-color rendering
- `ShadowTests` - Shadow effects with various geometries
- `ReflectionRefractionTests` - Reflective and transparent spheres
- `TeapotTest` - Complex 3D model (Utah Teapot)
- `SnowmanTest` - Composite scene with multiple spheres
- `DaimonTest` - Diamond rendering with refractions

---

## 🏛️ Architecture

### Ray Tracing Pipeline

```
┌─────────────┐
│   Camera    │ Creates rays for each pixel
└──────┬──────┘
       │
       ▼
┌─────────────┐
│ Ray Tracer  │ Traces rays through the scene
└──────┬──────┘
       │
       ▼
┌─────────────┐
│   Scene     │ Finds ray-geometry intersections
└──────┬──────┘
       │
       ▼
┌─────────────┐
│  Lighting   │ Calculates color using Phong model
└──────┬──────┘
       │
       ▼
┌─────────────┐
│Image Writer │ Outputs PNG image
└─────────────┘
```

### Key Algorithms

1. **Ray-Geometry Intersection**
   - Analytic solutions for primitives
   - Bounding volume hierarchy for optimization

2. **Phong Illumination Model**
   - Ambient + Diffuse + Specular components
   - Multiple light source support

3. **Recursive Ray Tracing**
   - Reflection rays for mirror surfaces
   - Refraction rays for transparent materials
   - Depth-limited recursion

4. **Shadow Rays**
   - Point-to-light shadow rays
   - Soft shadow implementation

---

## 👥 Contributors

This project was developed by:

- **Student IDs**: 7061, 3114
- **Course**: ISE5784 - Introduction to Software Engineering
- **Institution**: Jerusalem College of Engineering

### 🙏 Acknowledgments
- Course instructor: Dan Zilberstein
- Initial test framework and structure provided by the course

---

## 📚 Course Information

**Course Name**: Introduction to Software Engineering (ISE5784)  
**Academic Year**: 5784 (2023-2024)  
**Institution**: Jerusalem College of Engineering  

### Learning Objectives Met
- ✅ Object-Oriented Programming principles
- ✅ Design patterns implementation
- ✅ Unit testing with JUnit
- ✅ Version control with Git
- ✅ Software architecture and design
- ✅ Algorithm implementation and optimization

---

## 📄 License

This project is part of an academic course assignment. All rights reserved to the course instructors and students.

### Usage Terms
- 📖 This code is for **educational purposes only**
- ❌ Do not copy for other course submissions
- ✅ Feel free to learn from the implementation
- ✅ Contributions and improvements are welcome

---

## 🌐 Connect

**Repository**: [ISE5784_7061_3114](https://github.com/amitmoradov/ISE5784_7061_3114)

---

<div align="center">

### ⭐ If you found this project helpful, please consider giving it a star!

**Made with ❤️ and ☕ by Software Engineering Students**

</div>
