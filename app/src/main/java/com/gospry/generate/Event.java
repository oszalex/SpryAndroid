/* Copyright (c) 2013, Richard Plangger <rich@pasra.at> All rights reserved.
 *
 * Android Record version 0.1.0 generated this file. For more
 * information see http://record.pasra.at/
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 *
 * This copyright notice must not be modified or deleted.
 */
package com.gospry.generate;

import com.gospry.suggestion.Suggestion;
import com.gospry.suggestion.SuggestionDate;
import com.gospry.suggestion.SuggestionLocation;
import com.gospry.suggestion.SuggestionTag;
import com.gospry.suggestion.SuggestionTime;

public class Event extends AbstractEvent {


    private String Location;

    public Event() {
        super(null);
        //TODO: Maximal 500leute einladbar?!
        mMaxAttending = 500;
    }

    public void set(Suggestion suggest) {   //TODO: CAST is not so nice
        switch (suggest.getType()) {
            case PERSON:
                break;
            case TIME:
                SuggestionTime time = (SuggestionTime) suggest;
                this.setStartTime(time.getStarttime());
                break;

            case DATE:
                SuggestionDate date = (SuggestionDate) suggest;
                this.setStartTime(date.getStartdate());
                break;
            case PLACE:
                SuggestionLocation location = (SuggestionLocation) suggest;
                this.setLocation(location.getLocation());
                break;
            case TAG:
                SuggestionTag tag = (SuggestionTag) suggest;
                this.setDescription(tag.getTag());
                break;
            default:
                break;
        }
    }

    @Override
    public void setStartTime(java.lang.Long value) {
        if (mStartTime == 0L) mStartTime = value;
        else mStartTime += value;
    }

    public boolean isAllSet() {
        //TODO: Check ob alle Daten gesetzt sind um ein neues Event zu erstellen
        return true;
    }

}
