=======================
 Welcome to Distutils2
=======================

Distutils2 is the packaging library that supersedes Distutils.  It has three
main audiences:

- Python authors who want to distribute their code
- End users who want to install modules or applications
- Developers of packaging-related tools who need a support library to
  build on

Authors will have to write a ``setup.cfg`` file and run a few
commands to package and distribute their code.  End users will be able to
search for, install and remove Python projects with the included
``pysetup`` program.  Last, developers will be able to reuse classes and
functions in their tools.

The Distutils2 codebase is a fork of Distutils.  It is not backward compatible
with Distutils and does not depend on it.  It provides more features and
implements new packaging standards.  In Python 3.3, Distutils2 is included in
the standard library under the module name "packaging".  Documentation is
provided at http://docs.python.org/dev/packaging --for ease of maintenance, it
is not duplicated in this repository.  You can use the Packaging documentation
to use Distutils2; only the package name is different (packaging vs.
distutils2), all modules, classes and functions have the same name.

If you want to contribute, please have a look at DEVNOTES.txt or
http://wiki.python.org/Distutils2/Contributing .

Beware that Distutils2 is still in alpha stage and its API is subject to
change.  It should be not used for critical deployments.  That said, it
is possible to start using it while keeping compatiblity with tools based
on the old Distutils or Setuptools, and the developers are eager to get
feedback from authors, end users and developers.

Useful links:

- Mailing list: http://mail.python.org/mailman/listinfo/distutils-sig/
- Mailing list with friendly tutors to guide new contributors:
  http://mail.python.org/mailman/listinfo/core-mentorship
- Repository: http://hg.python.org/distutils2
- Bug tracker: http://bugs.python.org/ (component "Distutils2")
