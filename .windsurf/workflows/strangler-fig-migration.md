---
description: Strangler Fig Migration Strategy for GeoServer Modules
---

# Strangler Fig Migration Analysis Workflow

This workflow guides you through analyzing GeoServer modules and selecting the best candidate for strangler fig migration, focusing on modules with the fewest dependencies.

## Phase 1: Codebase Understanding

1. **Map module structure**
```bash
find src -type d -maxdepth 1 | grep -v "^src$" | sort
```

2. **Identify module types**
```bash
# Look for different module categories
ls src/ | grep -E "(rest|web|wms|wfs|wcs|security|main)"
```

3. **Analyze module sizes**
```bash
for dir in src/*/; do
  echo "=== $(basename $dir) ==="
  find $dir -name "*.java" | wc -l
done | sort -k2 -n
```

## Phase 2: Dependency Analysis

4. **Generate dependency trees**
```bash
# Create dependency reports for key modules
mvn dependency:tree -pl :gs-restconfig-wms > deps-restconfig-wms.txt
mvn dependency:tree -pl :gs-monitor > deps-monitor.txt
mvn dependency:tree -pl :gs-rest > deps-rest.txt
```

5. **Count direct dependencies per module**
```bash
for module in src/*/pom.xml; do
  echo "=== $(dirname $module) ==="
  grep -c "<dependency>" $module 2>/dev/null || echo "0"
done | sort -k2 -n
```

6. **Identify internal vs external dependencies**
```bash
# Check for gs-* internal dependencies
grep -r "<artifactId>gs-" src/*/pom.xml | cut -d: -f1,3 | sort
```

## Phase 3: Module Isolation Assessment

7. **Check reverse dependencies**
```bash
# Find what depends on each module
for module in $(ls src/); do
  echo "=== Modules depending on $module ==="
  grep -r "gs-$module" src/*/pom.xml | cut -d/ -f2 | sort -u
done
```

8. **Analyze API boundaries**
```bash
# Find REST controllers and endpoints
find src -name "*.java" -exec grep -l "@RestController\|@RequestMapping" {} \;
```

9. **Document external interfaces**
```bash
# Find servlet configurations
find src -name "web.xml" -o -name "*servlet*.xml"
# Find Spring configurations
find src -name "applicationContext*.xml"
```

## Phase 4: Migration Candidate Scoring

10. **Score modules by migration complexity**
    - **Low complexity** (< 3 dependencies, no gs-main dependency)
    - **Medium complexity** (3-5 dependencies, minimal gs-main usage)
    - **High complexity** (> 5 dependencies, heavy gs-main integration)

11. **Evaluate business impact**
    - Configuration modules: Low risk
    - Utility modules: Low-medium risk  
    - Core service modules: High risk
    - Main/OWS modules: Very high risk

12. **Assess technical debt**
```bash
# Check for legacy patterns
grep -r "javax\.servlet" src/*/src/main/java/ | wc -l
grep -r "org\.springframework\.web\.servlet" src/*/src/main/java/ | wc -l
```

## Decision Matrix

| Module | Dependencies | Reverse Deps | API Complexity | Business Risk | Migration Score |
|--------|-------------|--------------|----------------|---------------|----------------|
| restconfig-wms | 2 | 0 | Low | Low | ⭐⭐⭐⭐⭐ |
| monitor | 3 | 1 | Low | Low | ⭐⭐⭐⭐ |
| rest-util | 4 | 2 | Medium | Medium | ⭐⭐⭐ |

## Recommended Selection Criteria

**Ideal First Candidates:**
- `restconfig-*` modules (configuration only)
- `monitor` module (metrics/monitoring)
- Utility modules with < 3 dependencies

**Avoid Initially:**
- `main`, `ows` (core dependencies)
- `web` (complex UI integration)
- Modules with > 5 reverse dependencies