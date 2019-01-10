package cc.doctor.rpc.springboot;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class AndTypeFilter implements TypeFilter {
    private TypeFilter[] typeFilters;

    public AndTypeFilter(TypeFilter... typeFilters) {
        this.typeFilters = typeFilters;
    }

    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        boolean match = true;
        for (TypeFilter typeFilter : typeFilters) {
            match = match && typeFilter.match(metadataReader, metadataReaderFactory);
        }
        return match;
    }
}
