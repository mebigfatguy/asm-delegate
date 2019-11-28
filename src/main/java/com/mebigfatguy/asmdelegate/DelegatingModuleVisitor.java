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

import org.objectweb.asm.ModuleVisitor;

public class DelegatingModuleVisitor extends ModuleVisitor {

    private ModuleVisitor[] moduleVisitors;

    public DelegatingModuleVisitor(int api, ModuleVisitor... visitors) {
        super(api);
        moduleVisitors = visitors;
    }

    @Override
    public void visitMainClass(String mainClass) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitMainClass(mainClass);
            }
        }
    }

    @Override
    public void visitPackage(String packaze) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitPackage(packaze);
            }
        }
    }

    @Override
    public void visitRequire(String module, int access, String version) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitRequire(module, access, version);
            }
        }
    }

    @Override
    public void visitExport(String packaze, int access, String... modules) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitExport(packaze, access, modules);
            }
        }
    }

    @Override
    public void visitOpen(String packaze, int access, String... modules) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitOpen(packaze, access, modules);
            }
        }
    }

    @Override
    public void visitUse(String service) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitUse(service);
            }
        }
    }

    @Override
    public void visitProvide(String service, String... providers) {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitProvide(service, providers);
            }
        }
    }

    @Override
    public void visitEnd() {
        for (ModuleVisitor mv : moduleVisitors) {
            if (mv != null) {
                mv.visitEnd();
            }
        }
    }

}
