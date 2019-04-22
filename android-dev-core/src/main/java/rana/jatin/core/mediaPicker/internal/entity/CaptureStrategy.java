/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rana.jatin.core.mediaPicker.internal.entity;

public class CaptureStrategy {

    public final boolean isPublic;
    public final String authority;
    public final String directory;
    public boolean image = true;
    public boolean video = true;

    public CaptureStrategy(boolean isPublic, String authority, String directory, boolean image, boolean video) {
        this.isPublic = isPublic;
        this.authority = authority;
        this.directory = directory;
        this.image = image;
        this.video = video;
    }

    public CaptureStrategy(boolean isPublic, String authority, boolean image, boolean video) {
        this.isPublic = isPublic;
        this.authority = authority;
        this.directory = null;
        this.image = image;
        this.video = video;
    }

    public CaptureStrategy(boolean isPublic, String authority) {
        this(isPublic, authority, null);
    }

    public CaptureStrategy(boolean isPublic, String authority, String directory) {
        this.isPublic = isPublic;
        this.authority = authority;
        this.directory = directory;
    }

}
