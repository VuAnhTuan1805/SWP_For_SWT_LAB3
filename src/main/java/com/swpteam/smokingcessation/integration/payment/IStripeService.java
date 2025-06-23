package com.swpteam.smokingcessation.integration.payment;

import com.stripe.model.Event;
import com.swpteam.smokingcessation.domain.dto.payment.StripeResponse;
import com.swpteam.smokingcessation.domain.dto.payment.StripeSubscriptionRequest;

public interface IStripeService {

    StripeResponse checkoutSubscription(StripeSubscriptionRequest request);

    void handleCheckoutSessionCompleted(Event event);

    void handlePaymentIntentSucceeded(Event event);
}
