import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { MylastpornTipoModule } from './tipo/tipo.module';
import { MylastpornVideoModule } from './video/video.module';
import { MylastpornHistoriaModule } from './historia/historia.module';
import { MylastpornFotoModule } from './foto/foto.module';
import { MylastpornPaginaModule } from './pagina/pagina.module';
import { MylastpornSeguidoresPaginaModule } from './seguidores-pagina/seguidores-pagina.module';
import { MylastpornTagsModule } from './tags/tags.module';
import { MylastpornModulosModule } from './modulos/modulos.module';
import { MylastpornLikesModule } from './likes/likes.module';
import { MylastpornDenunciasModule } from './denuncias/denuncias.module';
import { MylastpornTipoDenunciaModule } from './tipo-denuncia/tipo-denuncia.module';
import { MylastpornFavoritosModule } from './favoritos/favoritos.module';
import { MylastpornVisibilidadeModule } from './visibilidade/visibilidade.module';
import { MylastpornComentariosModule } from './comentarios/comentarios.module';
import { MylastpornAmigosModule } from './amigos/amigos.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        MylastpornTipoModule,
        MylastpornVideoModule,
        MylastpornHistoriaModule,
        MylastpornFotoModule,
        MylastpornPaginaModule,
        MylastpornSeguidoresPaginaModule,
        MylastpornTagsModule,
        MylastpornModulosModule,
        MylastpornLikesModule,
        MylastpornDenunciasModule,
        MylastpornTipoDenunciaModule,
        MylastpornFavoritosModule,
        MylastpornVisibilidadeModule,
        MylastpornComentariosModule,
        MylastpornAmigosModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornEntityModule {}
