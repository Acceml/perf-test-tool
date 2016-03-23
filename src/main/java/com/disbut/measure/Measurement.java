/*
 *   Copyright (c) 2016. The Acceml.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.disbut.measure;

import com.disbut.RecordWriter;
import com.disbut.Recorder;

import java.io.IOException;

/**
 * Created by Acceml on 2016/3/22
 * Email: huminghit@gmail.com
 */
public class Measurement extends Measure implements Recorder {

    private final String name;

    private volatile boolean activate = false;

    public Measurement(String name) {
        this.name = name;
    }

    /**
     * start measure.
     */
    public void setActivate() {
        activate = true;
    }

    public void stop() {
        activate = false;
    }

    @Override
    public void recordWrite(RecordWriter recordWriter) {
        try {
            recordWriter.write(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
