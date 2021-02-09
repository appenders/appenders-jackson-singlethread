package org.appenders.st.jackson;

/*-
 * #%L
 * appenders-jackson-st
 * %%
 * Copyright (C) 2020 Appenders Project
 * %%
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
 * #L%
 */

import com.fasterxml.jackson.core.json.JsonWriteContext;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class JsonWriteContextAccessorTest {

    @Test
    public void resetResetsCurrentContextImmediatelyIfContextHasNoParent() {

        // given
        JsonWriteContextAccessor ctxAccess = new JsonWriteContextAccessor();
        JsonWriteContext context = JsonWriteContext.createRootContext(null);

        // when
        JsonWriteContext ctxAfterReset = ctxAccess.reset(context);

        // then
        assertSame(context, ctxAfterReset);
        assertTrue(ctxAccess.inRoot(ctxAfterReset));

    }

    @Test
    public void resetResetsAndReturnsRootContextIfContextHasParent() {

        // given
        JsonWriteContextAccessor ctxAccess = new JsonWriteContextAccessor();
        JsonWriteContext grandpaContext = JsonWriteContext.createRootContext(null);
        JsonWriteContext parentContext = grandpaContext.createChildObjectContext();
        JsonWriteContext childContext = parentContext.createChildObjectContext();

        assertFalse(ctxAccess.inRoot(childContext));

        // when
        JsonWriteContext ctxAfterReset = ctxAccess.reset(childContext);

        // then
        assertSame(grandpaContext, ctxAfterReset);
        assertTrue(ctxAccess.inRoot(ctxAfterReset));

    }

}