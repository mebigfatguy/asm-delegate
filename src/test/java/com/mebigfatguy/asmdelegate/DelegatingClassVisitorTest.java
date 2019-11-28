/*
 * asm-delegate - a set of asm visitors that allows multiple visitors to be used at the same time, simply
 *
 * Copyright 2019-2019 MeBigFatGuy.com
 * Copyright 2019-2019 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.asmdelegate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

public class DelegatingClassVisitorTest {

    @Test
    public void testSimpleDelegation() throws IOException {

        List<Class> visitors = new ArrayList<>(2);
        DelegatingClassVisitor dcv = new DelegatingClassVisitor(Opcodes.ASM7, new CV1(visitors), new CV2(visitors));

        try (InputStream is = DelegatingClassVisitorTest.class
                .getResourceAsStream("/" + DelegatingClassVisitorTest.class.getName().replace('.', '/') + ".class")) {
            ClassReader r = new ClassReader(is);
            r.accept(dcv, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        }

        Assert.assertEquals(2, visitors.size());
        Assert.assertEquals(CV1.class, visitors.get(0));
        Assert.assertEquals(CV2.class, visitors.get(1));
    }

    public static class CV1 extends ClassVisitor {

        List<Class> visitors;

        public CV1(List<Class> visitedVisitors) {
            super(Opcodes.ASM7);
            visitors = visitedVisitors;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName,
                String[] interfaces) {
            visitors.add(CV1.class);
        }

    }

    public static class CV2 extends ClassVisitor {

        List<Class> visitors;

        public CV2(List<Class> visitedVisitors) {
            super(Opcodes.ASM7);
            visitors = visitedVisitors;
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName,
                String[] interfaces) {
            visitors.add(CV2.class);
        }

    }
}
