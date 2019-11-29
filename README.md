# asm-delegate

A set of classes that allow for delegating visitors to a set of visitors. This allows you do use many visitors in one pass of a ClassReader, but keep the logic for each time of scan separate in their own visitor. Used Like

```java
MyClassVisitor1 v1 = new MyClassVisitor1();
MyClassVisitor2 v2 = new MyClassVisitor2();
MyClassVisitor3 v3 = new MyClassVisitor3();

DelegatingClassVisitor dcv = new DelegatingClassVisitor(Opcodes.ASM7, v1, v2, v3);

ClassReader r = new ClassReader(inputStream, 0);
r.accept(dcv);
```
Delegating visitors are available for all ASM visitors: Class, Field, Method, Annotation, Module

Available on maven central with coordinates

| GroupId    | com.mebigfatguy.asm-delegate |
| ArtifactId | asm-delegate                 |
| Version    | 0.2.0                        |


