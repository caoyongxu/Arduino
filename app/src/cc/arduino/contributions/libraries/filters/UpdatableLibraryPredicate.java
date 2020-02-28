/*
 * This file is part of Arduino.
 *
 * Copyright 2015 Arduino LLC (http://www.arduino.cc/)
 *
 * Arduino is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, you may use this file as part of a free software
 * library without restriction.  Specifically, if other files instantiate
 * templates or use macros or inline functions from this file, or you compile
 * this file and link it with other files to produce an executable, this
 * file does not by itself cause the resulting executable to be covered by
 * the GNU General Public License.  This exception does not however
 * invalidate any other reasons why the executable file might be covered by
 * the GNU General Public License.
 */

package cc.arduino.contributions.libraries.filters;

import java.util.List;
import java.util.function.Predicate;

import cc.arduino.contributions.VersionComparator;
import cc.arduino.contributions.libraries.ContributedLibraryRelease;
import cc.arduino.contributions.libraries.LibrariesIndexer;
import processing.app.BaseNoGui;

public class UpdatableLibraryPredicate implements Predicate<ContributedLibraryRelease> {

  LibrariesIndexer librariesIndexer;

  public UpdatableLibraryPredicate() {
    librariesIndexer = BaseNoGui.librariesIndexer;
  }

  public UpdatableLibraryPredicate(LibrariesIndexer indexer) {
    librariesIndexer = indexer;
  }

  @Override
  public boolean test(ContributedLibraryRelease lib) {
    if (!lib.isLibraryInstalled()) {
      return false;
    }
    String libraryName = lib.getName();
    List<ContributedLibraryRelease> libraries = librariesIndexer.getIndex().find(libraryName);
    ContributedLibraryRelease latest = libraries.stream().reduce(VersionComparator::max).get();
    return !latest.isLibraryInstalled();
  }
}
