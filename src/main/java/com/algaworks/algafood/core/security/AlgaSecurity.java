package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.OrderRepository;
import com.algaworks.algafood.domain.repository.RestaurantRepository;

@Component
public class AlgaSecurity {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Retorna a autenticação atual do contexto.
     * 
     * @return Dados da autenticação atual.
     */
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    /**Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário do contexto está autenticado.
     * 
     * @return Retorna true caso esteja autenticado e false caso contrário.
     */
    public boolean isAuthenticated() {
        return getAuthentication().isAuthenticated();
    }

    /**
     * Como estamos usando JWT na aplicação o getPrincipal() retorna uma instância
     * de JWT o que nos permite fazer o casting e trabalhar com os clains do token.
     * 
     * @return ID do usuário no contexto atual.
     */
    public Long getUserId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();
        return jwt.getClaim("user_id");
    }    

    /**
     * Verifica se usuário autenticado no contexto tem a permissão informada por
     * parâmetro.
     * 
     * @param authorityName Nome da authority que se deseja verificar se o usuário
     *                      do contexto à possui.
     * @return Retorna true caso o usuário tenha a permissão e false caso não tenha.
     */
    private boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }
    
    /**
     * Usado nas anotações @Security para Validação personalizada para regras de
     * negócio. Verifica se o usuário em questão é um responsável pelo restaurante.
     * 
     * @param restaurantId ID do restaurante atual indicado no path da requisição.
     * @return retorna true caso o usuário do contexto atual seja um responsável
     *         pelo restaurante e retorna falso caso o mesmo não seja.
     */
    public boolean isUserManagerOfRestaurants(Long restaurantId) {
        if (restaurantId == null) {
            return false;
        }

        return restaurantRepository.existsUserManager(restaurantId, getUserId());
    }

    /**
     * Usado nas anotações @Security para Validação personalizada para regras de
     * negócio. Verifica se o usuário em questão está autorizado a movimentar os
     * status de um pedido.
     * 
     * @param code Código do pedido.
     * @return Retorna true caso o usuário tenha permissão para gerenciar o pedido e
     *         false caso o mesmo não tenha permissão.
     */
    public boolean managedRestaurantOfOrder(String code) {
        return orderRepository.isOrderManagedBy(code, getUserId());
    }

    /**
     * Usado nas anotações @Security para Validação personalizada para regras de
     * negócio. Verifica se o usuário autenticado no contexto é o mesmo usuário
     * utilizado como filtro de pesquisa nas requisições.
     * 
     * @param userId ID do usuário que se deseja verificar a igualdade com o usuário
     *               do contexto.
     * @return Retorna true caso o usuário recebido por parâmetro seja o mesmo
     *         usuário do contexto, e false caso o usuário recebido por parâmetro
     *         não seja igual ao usuário do contexto.
     */
    public boolean athenticatedUserIsEquals(Long userId) {
        return getUserId() != null && userId != null && getUserId().equals(userId);
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário tem a permissão de escrita.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean hasScopeWrite() {
        return hasAuthority("SCOPE_WRITE");
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário tem a permissão de leitura.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean hasScopeRead() {
        return hasAuthority("SCOPE_READ");
    }

    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário do contexto tem a permissão SCOPE_WRITE e também
     * se o mesmo possui a permissão GERENCIAR_PEDIDOS ou se é um usuário
     * responsável pelo restaurante do pedido. ou se
     * 
     * @param code Código do pedido.
     * @return Retorna true caso atenda as condições listadas acima e false caso não
     *         atenda.
     */
    public boolean canManageOrders(String code) {
        return isAuthenticated() && hasScopeWrite() && (hasAuthority("GERENCIAR_PEDIDOS") || managedRestaurantOfOrder(code));
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar restaurantes.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingRestaurants() {
        return isAuthenticated() && hasScopeRead();
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para editar restaurantes.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canEditingRestaurants() {
        return isAuthenticated() && hasScopeWrite() && hasAuthority("EDITAR_RESTAURANTES");
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para gerenciar restaurantes.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canManageRestaurants(Long restaurantId) {
        return isAuthenticated() && hasScopeWrite() && (hasAuthority("EDITAR_RESTAURANTES") || isUserManagerOfRestaurants(restaurantId));
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar usuários, grupos e
     * permissões.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingUsersGroupsPermissions() {
        return isAuthenticated() && hasScopeRead() && hasAuthority("CONSULTAR_USUARIOS_GRUPOS_PERMISSOES");
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para editar usuários, grupos e
     * permissões.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canEditingUsersGroupsPermissions() {
        return isAuthenticated() && hasScopeWrite() && hasAuthority("EDITAR_USUARIOS_GRUPOS_PERMISSOES");
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para editar formas de pagamento.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canEditingPaymentForms() {
        return isAuthenticated() && hasScopeWrite() && hasAuthority("EDITAR_FORMAS_PAGAMENTO");
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar todos os pedidos
     * de um determinado restaurante.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingAllOrders(Long userId, Long restaurantId) {
        return canConsultingOrders() && (hasAuthority("CONSULTAR_PEDIDOS") || athenticatedUserIsEquals(userId) || isUserManagerOfRestaurants(restaurantId));
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar pedidos.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingOrders() {
        return isAuthenticated() && hasScopeRead();
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar formas de pagamento.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingPaymentForms() {
        return isAuthenticated() && hasScopeRead();
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar cidades.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingCities() {
        return isAuthenticated() && hasScopeRead();
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar estados.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingStates() {
        return isAuthenticated() && hasScopeRead();
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para consultar cozinhas.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingKitchens() {
        return isAuthenticated() && hasScopeRead();
    }
    
    /**
     * Utilizado para geração dos links de hypermidea no padrão HAL dinâmicamente e
     * tambem nas anotações @Security para Validação personalizada de regras de
     * negócio. Valida se o usuário possuí permissão para gerar relatórios.
     * 
     * @return Retorna true caso possua e false caso contrário.
     */
    public boolean canConsultingStatistics() {
        return isAuthenticated() && hasScopeRead() && hasAuthority("GERAR_RELATORIOS");
    }

}
